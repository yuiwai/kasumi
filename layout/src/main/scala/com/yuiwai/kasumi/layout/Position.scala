package com.yuiwai.kasumi.layout

import com.yuiwai.kasumi.core.implementation.{Board, Node}
import com.yuiwai.kasumi.layout.Layout.NodeMap

import scala.collection.immutable.Queue
import scala.language.existentials

final case class Position(x: Int, y: Int) {
  def map(f: (Int, Int) => (Int, Int)): Position = Position.tupled(f(x, y))
}
object Layout {
  type NodeMap = Map[Node[_], Position]
}

object Builder {
  def build(board: Board): NodeMap = {
    board.nodes.headOption
      .map(n => buildImpl(Map(n -> Position(0, 0)), Queue(n))(board))
      .getOrElse(Map.empty)
  }
  private def buildImpl(nodeMap: NodeMap, queue: Queue[Node[_]])(implicit board: Board): NodeMap = {
    val (n, q) = queue.dequeue
    val ns = board
      .edges
      .collect { case e if e.from == n && !nodeMap.contains(e.to) => e.to }
    if (ns.isEmpty) nodeMap
    else {
      buildImpl(
        addNodesToNodeMap(nodeMap, n, ns),
        q.enqueueAll(ns)
      )
    }
  }
  private def addNodesToNodeMap(nodeMap: NodeMap, current: Node[_], newNodes: Set[Node[_]])
    (implicit board: Board): NodeMap = {
    newNodes.zipWithIndex.foldLeft(nodeMap) {
      case (nm, (n, i)) => nm.updated(n, calcPosition(nm(current), newNodes.size, i))
    }
  }
  private def calcPosition(current: Position, count: Int, index: Int): Position = current.map(_ + 1 -> _)
}
