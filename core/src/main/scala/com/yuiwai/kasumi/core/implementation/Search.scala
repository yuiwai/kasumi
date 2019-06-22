package com.yuiwai.kasumi.core.implementation

import com.yuiwai.kasumi.core.concept.SearchOps

import scala.collection.immutable.Queue

trait BFSImpl extends SearchOps[Board, Node[_], Edge, Route] {
  type N = Node[_]
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
object BFS extends BFSImpl
