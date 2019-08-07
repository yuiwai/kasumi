package com.yuiwai.kasumi.visualize

import com.yuiwai.kasumi.core.implementation.TypedBoard
import com.yuiwai.kasumi.examples.stations.{Data, Line, Station}
import com.yuiwai.yachiyo.ui
import com.yuiwai.yachiyo.ui.{CanvasView, NoCallback, SceneCallback, SceneSuite}
import com.yuiwai.yachiyo.zio.ApplicationHandler
import com.yuiwai.yachiyo.zio.ApplicationHandler.AppEnv
import org.scalajs.dom
import org.scalajs.dom.ext.KeyCode
import org.scalajs.dom.raw._
import zio.{App, ZIO}

import scala.util.chaining._

object Main extends App {
  override def run(args: List[String]): ZIO[Main.Environment, Nothing, Unit] =
    for {
      _ <- ApplicationHandler
        .program
        .provide(AppEnv.unsafeInit(VisualizeApp))
        .fold(_ => (), _ => ())
    } yield ()
}


object VisualizeApp extends ui.Application {
  val stationsKey = 1
  override val sceneSuiteMap: Map[Int, SceneSuite] = Map(
    stationsKey -> SceneSuite(
      () => StationsScene,
      () => StationsPresenter,
      () => new StationsView
    )
  )
  override def initialSceneSuiteKey: Int = stationsKey
}

final case class StationsState(
  line: Line,
  lineStations: Seq[Station],
  stations: TypedBoard[Station],
  currentStation: Station) {
  def next: StationsState = lineStations
    .dropWhile(_ != currentStation)
    .pipe {
      case _ :: t :: _ => copy(currentStation = t)
      case _ => this
    }
  def prev: StationsState = lineStations
    .reverse
    .dropWhile(_ != currentStation)
    .pipe {
      case _ :: t :: _ => copy(currentStation = t)
      case _ => this
    }
}
object StationsState {
  def apply(line: Line, stations: TypedBoard[Station]): StationsState = {
    stations
      .nodes
      .collect { case n if n.value.lines.contains(line) => n.value }
      .toSeq
      .sortBy(_.number)
      .pipe(ls => apply(line, ls, stations, ls.head))
  }
}
object StationsScene extends ui.Scene {
  override type State = StationsState
  override type Command = StationsCommand
  override type Event = None.type
  type R = (StationsState, None.type, SceneCallback)

  sealed trait StationsCommand
  case object MoveDown extends StationsCommand
  case object MoveUp extends StationsCommand

  override def initialState(): StationsState =
    StationsState(Data.lineByName("東西線").get, Data.stations)
  override def execute(state: StationsState, input: StationsCommand): R = input match {
    case MoveDown => (state.next, None, NoCallback)
    case MoveUp => (state.prev, None, NoCallback)
    case _ => (state, None, NoCallback)
  }
}

object StationsPresenter extends ui.Presenter {
  override type S = StationsScene.type
  override type M = StationsViewModel
  override def updated(state: StationsScene.State, prevModel: StationsPresenter.Prev): StationsViewModel = {
    StationsViewModel(
      state.line,
      state.lineStations,
      state.currentStation
    )
  }
}

final case class StationsViewModel(line: Line, stations: Seq[Station], currentStation: Station) extends ui.ViewModel
class StationsView extends ui.View with CanvasView {
  override type S = StationsScene.type
  override type M = StationsViewModel
  private var context: Option[CanvasRenderingContext2D] = None
  private def container = elementById("container")
  private val canvasWidth = 300
  private val canvasHeight = 300
  private val stationWidth = 20
  private val stationHeight = 15
  private val stationMargin = 20
  private val stationFontSize = 15
  override def setup(viewModel: StationsViewModel, listener: Listener): Unit = {
    val canvas = createCanvas(canvasWidth, canvasHeight)
    container.appendChild(canvas)
    context = Some(canvas.getContext("2d").asInstanceOf[CanvasRenderingContext2D])
    update(viewModel)

    dom.window.onkeydown = e => keyHandler(listener, e)
  }
  private def keyHandler(listener: Listener, e: KeyboardEvent): Unit = {
    e.keyCode match {
      case KeyCode.J => listener(StationsScene.MoveDown)
      case KeyCode.K => listener(StationsScene.MoveUp)
      case _ =>
    }
  }
  override def update(viewModel: StationsViewModel): Unit = context.foreach(draw(_, viewModel))
  private def draw(ctx: CanvasRenderingContext2D, viewModel: StationsViewModel): Unit = {
    ctx.clearRect(0, 0, canvasWidth, canvasHeight)
    ctx.font = "15px sans-serif"
    ctx.fillText(viewModel.line.name, 10, 18)
    drawStations(viewModel.stations, viewModel.currentStation, 0)(ctx)
  }
  private def drawStations(stations: Seq[Station], currentStation: Station, offset: Int)
    (implicit ctx: CanvasRenderingContext2D): Unit = {
    if (stations.nonEmpty) {
      drawStation(stations.head, stations.head == currentStation, offset)
      drawStations(stations.tail, currentStation, offset + 1)
    }
  }
  private def drawStation(station: Station, isCurrent: Boolean, offset: Int)
    (implicit ctx: CanvasRenderingContext2D): Unit = {
    ctx.fillStyle = if (isCurrent) "red" else "black"
    ctx.fillRect(10, (stationHeight + stationMargin) * (offset + 1), stationWidth, stationHeight)
    ctx.font = s"${stationFontSize}px sans-serif"
    ctx.fillText(
      station.name,
      stationWidth + 20,
      (stationHeight + stationMargin) * (offset + 1) + stationHeight - (stationHeight - stationFontSize) / 2 - 3)
  }
  override def cleanup(): Unit = {
    container.innerHTML = ""
    dom.window.onkeydown = null
  }
}
