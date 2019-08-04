package com.yuiwai.kasumi.visualize

import com.yuiwai.kasumi.core.implementation.TypedBoard
import com.yuiwai.kasumi.examples.stations.{Data, Line, Station}
import org.scalajs.dom
import org.scalajs.dom.raw._

import scala.util.chaining._

object Main {
  def main(args: Array[String]): Unit = {
    View.init()
    View.drawLine("東西線")
  }
}

case class DrawArea(width: Int, height: Int)
object View {
  lazy val stations: TypedBoard[Station] = Data.stations
  lazy val container: Element =
    dom.document.createElement("div")
      .tap { elem =>
        dom.document.body.appendChild(elem)
      }
  def init(): Unit = {
    dom.document.body.style =
      """
        |margin: 0;
      """.stripMargin
  }
  def clear(): Unit = {
    container.innerHTML = ""
  }
  def drawLine(lineName: String, stationName: Option[String] = None): Unit = {
    clear()
    container.appendChild(dom.document.createElement("h1").tap { e =>
      e.innerText = lineName
      e.setAttribute("style",
        """
          |text-align: center;
          |font-size: 1.2em;
        """.stripMargin)
    })
    val subContainer = dom.document.createElement("div")
      .pipe(_.asInstanceOf[HTMLElement])
      .tap { sc =>
        sc.setAttribute("style",
          """
            |position: relative;
            |border-bottom: 1px solid black;
            |border-top: 1px solid black;
            |height: 400px;
            |overflow-y: scroll;
          """.stripMargin)
        container.appendChild(sc)
      }
    Data.lineAsRouteByName(lineName).foreach { l =>
      l.nodes.map(_.value)
        .foreach { s =>
          drawStation(
            subContainer,
            s,
            Data.lineByName(lineName).get,
            isCurrent = stationName.getOrElse(l.from.value.name) == s.name
          )
        }
    }
  }
  def drawStation(parent: Element, station: Station, line: Line, isCurrent: Boolean = false): Unit = {
    val elem = dom.document.createElement("div")
    elem.innerText = station.name
    elem.setAttribute("style",
      s"""
         |padding: 15px;
         |font-size: 1.2em;
         |border: 1px solid skyblue;
         |text-align: center;
         |color: #333;
         |background-color: ${if (isCurrent) "#fcc" else "#fff"};
         |""".stripMargin)
    stations.edges
      .filter { x =>
        x.from.value == station && !x.to.value.line.contains(line)
      }
      .foreach { e =>
        val child = dom.document.createElement("div").asInstanceOf[HTMLElement].tap { x =>
          val s = e.to.value
          x.innerText = s" [${s.line.head.name}: ${s.name}]"
          x.onclick = _ => drawLine(s.line.head.name, Some(s.name))
          x.setAttribute("style",
            """
              |font-size: 0.8em;
              |cursor: pointer;
            """.stripMargin)
        }
        elem.appendChild(child)
      }
    parent.appendChild(elem)
  }
}
