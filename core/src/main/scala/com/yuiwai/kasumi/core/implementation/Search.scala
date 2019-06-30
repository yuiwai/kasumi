package com.yuiwai.kasumi.core.implementation

import com.yuiwai.kasumi.core.concept.{NodeOps, SearchOps, TypedSearchOps}

import scala.collection.immutable.Queue

trait BFSImpl extends SearchOps[Node[_]] {
  override type B = Board
  override def search(board: Board, from: N, to: N, condition: Condition = _ => true): Option[Route] =
    searchImpl(Queue(from), Map(from -> from))(board, to, condition)
  private def searchImpl(queue: Queue[N], visited: Map[N, N])
    (implicit board: Board, to: N, condition: Condition): Option[Route] = {
    if (queue.isEmpty) None
    else {
      queue.head match {
        case `to` => Some(routeFromVisited(visited, Edge(visited(to), to) :: Nil))
        case n =>
          val es = board.edges.filter(e => e.from == n && !visited.exists(_._1 == e.to) && condition(e))
          searchImpl(
            es.foldLeft(queue.tail)((q, e) => q.enqueue(e.to)),
            es.foldLeft(visited)((m, e) => m + (e.to -> n))
          )
      }
    }
  }
  private def routeFromVisited(visited: Map[N, N], edges: List[Edge]): Route = edges match {
    case Edge(f, t) :: x :: y if f == t => Route(x, y)
    case Edge(f, _) :: _ => routeFromVisited(visited, Edge(visited(f), f) :: edges)
  }
}
object BFS extends BFSImpl {
  def typed[V]: TypedBFSImpl[V] = new TypedBFSImpl[V] {}
}

trait TypedBFSImpl[V] extends TypedSearchOps[NodeOps[V], V] {
  override type B = TypedBoard[V]
  override def search(board: TypedBoard[V], from: N, to: N, condition: Condition): Option[R] =
    searchImpl(Queue(from), Map(from -> from))(board, to, condition)
  private def searchImpl(queue: Queue[N], visited: Map[N, N])
    (implicit board: TypedBoard[V], to: N, condition: Condition): Option[R] = {
    if (queue.isEmpty) None
    else {
      queue.head match {
        case `to` => Some(routeFromVisited(visited, TypedEdge(visited(to).asInstanceOf[Node[V]], to.asInstanceOf[Node[V]]) :: Nil))
        case n =>
          val es = board.edges.filter(e => e.from == n && !visited.exists(_._1 == e.to) && condition(e))
          searchImpl(
            es.foldLeft(queue.tail)((q, e) => q.enqueue(e.to)),
            es.foldLeft(visited)((m, e) => m + (e.to -> n))
          )
      }
    }
  }
  private def routeFromVisited(visited: Map[N, N], edges: List[TypedEdge[V]]): TypedRoute[V] = edges match {
    case TypedEdge(f, t) :: x :: y if f == t => TypedRoute(x, y)
    case TypedEdge(f, _) :: _ => routeFromVisited(visited, TypedEdge(visited(f).asInstanceOf[Node[V]], f.asInstanceOf[Node[V]]) :: edges)
  }
}
