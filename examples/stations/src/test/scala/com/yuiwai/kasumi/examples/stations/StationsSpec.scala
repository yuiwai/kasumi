package com.yuiwai.kasumi.examples.stations

import utest._

object StationsSpec extends TestSuite {
  val tests = Tests {
    "lines" - {
      Data.lines.size ==> Lines.all.size
      Data.lines.foreach { case (line, stations) =>
        stations.forall(_.lines.contains(line)) ==> true
        stations.size ==> Stations.all.count(s => s.lines.contains(line))
      }
    }
  }
}
