package com.yuiwai.kasumi.examples.stations

import com.yuiwai.kasumi.core.implementation._
import scala.util.chaining._

final case class Station(line: String, name: String)

final case class Line(mark: String, organization: String, name: String)
object Lines {
  val JB = Line("JB", "JR", "中央・総武線")
  val JY = Line("JY", "JR", "山手線")
  val G = Line("G", "東京メトロ", "銀座線")
  val H = Line("H", "東京メトロ", "日比谷線")
  val T = Line("T", "東京メトロ", "東西線")
  val C = Line("C", "東京メトロ", "千代田線")
  val Y = Line("Y", "東京メトロ", "有楽町線")
  val Z = Line("Z", "東京メトロ", "半蔵門線")
  val N = Line("N", "東京メトロ", "南北線")
  val F = Line("F", "東京メトロ", "副都心線")
  val KS = Line("KS", "京成電鉄", "") // KSの下に本線、成田空港線などがナンバーの範囲で分けられている
  val SL = Line("SL", "新京成電鉄", "新京成線")
}
object Stations {
  import Lines._
  final case class Station(line: Line, number: Int, name: String)
  val JB01 = Station(JB, 1, "三鷹")
  val JB02 = Station(JB, 2, "吉祥寺")
  val JB03 = Station(JB, 3, "西荻窪")
  val JB04 = Station(JB, 4, "荻窪")
  val JB05 = Station(JB, 5, "阿佐ヶ谷")
  val JB06 = Station(JB, 6, "高円寺")
  val JB07 = Station(JB, 7, "中野")
  val JB08 = Station(JB, 8, "東中野")
  val JB09 = Station(JB, 9, "大久保")
  val JB10 = Station(JB, 10, "新宿")
  val JB11 = Station(JB, 11, "代々木")
  val JB12 = Station(JB, 12, "千駄ヶ谷")
  val JB13 = Station(JB, 13, "信濃町")
  val JB14 = Station(JB, 14, "四谷")
  val JB15 = Station(JB, 15, "市ヶ谷")
  val JB16 = Station(JB, 16, "飯田橋")
  val JB17 = Station(JB, 17, "水道橋")
  val JB18 = Station(JB, 18, "御茶ノ水")
  val JB19 = Station(JB, 19, "秋葉原")
  val JB20 = Station(JB, 20, "浅草橋")
  val JB21 = Station(JB, 21, "両国")
  val JB22 = Station(JB, 22, "錦糸町")
  val JB23 = Station(JB, 23, "亀戸")
  val JB24 = Station(JB, 24, "平井")
  val JB25 = Station(JB, 25, "新小岩")
  val JB26 = Station(JB, 26, "小岩")
  val JB27 = Station(JB, 27, "市川")
  val JB28 = Station(JB, 28, "本八幡")
  val JB29 = Station(JB, 29, "下総中山")
  val JB30 = Station(JB, 30, "西船橋")
  val JB31 = Station(JB, 31, "船橋")
  val JB32 = Station(JB, 32, "東船橋")
  val JB33 = Station(JB, 33, "津田沼")
  val JB34 = Station(JB, 34, "幕張本郷")
  val JB35 = Station(JB, 35, "幕張")
  val JB36 = Station(JB, 36, "新検見川")
  val JB37 = Station(JB, 37, "稲毛")
  val JB38 = Station(JB, 38, "西千葉")
  val JB39 = Station(JB, 39, "千葉")

  val JY01 = Station(JY, 1, "東京")
  val JY02 = Station(JY, 2, "神田")
  val JY03 = Station(JY, 3, "秋葉原")
  val JY04 = Station(JY, 4, "御徒町")
  val JY05 = Station(JY, 5, "上野")
  val JY06 = Station(JY, 6, "鴬谷")
  val JY07 = Station(JY, 7, "日暮里")
  val JY08 = Station(JY, 8, "西日暮里")
  val JY09 = Station(JY, 9, "田端")
  val JY10 = Station(JY, 10, "駒込")
  val JY11 = Station(JY, 11, "巣鴨")
  val JY12 = Station(JY, 12, "大塚")
  val JY13 = Station(JY, 13, "池袋")
  val JY14 = Station(JY, 14, "目白")
  val JY15 = Station(JY, 15, "高田馬場")
  val JY16 = Station(JY, 16, "新大久保")
  val JY17 = Station(JY, 17, "新宿")
  val JY18 = Station(JY, 18, "代々木")
  val JY19 = Station(JY, 19, "原宿")
  val JY20 = Station(JY, 20, "渋谷")
  val JY21 = Station(JY, 21, "恵比須")
  val JY22 = Station(JY, 22, "目黒")
  val JY23 = Station(JY, 23, "五反田")
  val JY24 = Station(JY, 24, "大崎")
  val JY25 = Station(JY, 25, "品川")
  val JY27 = Station(JY, 27, "田町")
  val JY28 = Station(JY, 28, "浜松町")
  val JY29 = Station(JY, 29, "新橋")
  val JY30 = Station(JY, 30, "有楽町")

  val G01 = Station(G, 1, "渋谷")
  val G02 = Station(G, 2, "表参道")
  val G03 = Station(G, 3, "外苑前")
  val G04 = Station(G, 4, "青山一丁目")
  val G05 = Station(G, 5, "赤坂見附")
  val G06 = Station(G, 6, "溜池山王")
  val G07 = Station(G, 7, "虎ノ門")
  val G08 = Station(G, 8, "新橋")
  val G09 = Station(G, 9, "銀座")
  val G10 = Station(G, 10, "京橋")
  val G11 = Station(G, 11, "日本橋")
  val G12 = Station(G, 12, "三越前")
  val G13 = Station(G, 13, "神田")
  val G14 = Station(G, 14, "末広町")
  val G15 = Station(G, 15, "上野広小路")
  val G16 = Station(G, 16, "上野")
  val G17 = Station(G, 17, "稲荷町")
  val G18 = Station(G, 18, "田原町")
  val G19 = Station(G, 19, "浅草")

  val H01 = Station(H, 1, "中目黒")
  val H02 = Station(H, 2, "恵比須")
  val H03 = Station(H, 3, "広尾")
  val H04 = Station(H, 4, "六本木")
  val H05 = Station(H, 5, "神谷町")
  val H06 = Station(H, 6, "霞ヶ関")
  val H07 = Station(H, 7, "日比谷")
  val H08 = Station(H, 8, "銀座")
  val H09 = Station(H, 9, "東銀座")
  val H10 = Station(H, 10, "築地")
  val H11 = Station(H, 11, "八丁堀")
  val H12 = Station(H, 12, "茅場町")
  val H13 = Station(H, 13, "人形町")
  val H14 = Station(H, 14, "小伝馬町")
  val H15 = Station(H, 15, "秋葉原")
  val H16 = Station(H, 16, "仲御徒町")
  val H17 = Station(H, 17, "上野")
  val H18 = Station(H, 18, "入谷")
  val H19 = Station(H, 19, "三ノ輪")
  val H20 = Station(H, 10, "南千住")
  val H21 = Station(H, 21, "北千住")

  // T C Y Z

  val junctions: Seq[Set[Station]] = ???
}

object Data {
  type StrPair = (String, String)
  val lines: Map[String, List[String]] = Map(
    "JR中央・総武線" ->
      List("三鷹", "吉祥寺", "西荻窪", "荻窪", "阿佐ヶ谷", "高円寺", "中野", "東中野", "大久保", "新宿", "代々木", "千駄ヶ谷",
        "信濃町", "四谷", "市ヶ谷", "飯田橋", "水道橋", "御茶ノ水", "秋葉原", "浅草橋", "両国", "錦糸町", "亀戸", "平井",
        "新小岩", "小岩", "市川", "本八幡", "下総中山", "西船橋", "船橋", "東船橋", "津田沼", "幕張本郷", "幕張", "新検見川",
        "稲毛", "西千葉", "千葉"),
    "JR山手線" ->
      List("東京", "神田", "秋葉原", "御徒町", "上野", "鴬谷", "日暮里", "西日暮里", "田端", "駒込", "巣鴨", "大塚", "池袋",
        "目白", "高田馬場", "新大久保", "新宿", "代々木", "原宿", "渋谷", "恵比須", "目黒", "五反田", "大崎", "品川",
        "田町", "浜松町", "新橋", "有楽町"),
    "東京メトロ千代田線" ->
      List("代々木上原", "代々木公園", "明治神宮前", "表参道", "乃木坂", "赤坂", "国会議事堂前", "霞ヶ関", "日比谷", "二重橋前",
        "大手町", "新御茶ノ水", "湯島", "根津", "千駄木", "西日暮里", "町屋", "北千住", "綾瀬", "北綾瀬"),
    "東京メトロ日比谷線" ->
      List("中目黒", "恵比須", "広尾", "六本木", "神谷町", "霞ヶ関", "日比谷", "銀座", "東銀座", "築地", "八丁堀", "茅場町",
        "人形町", "小伝馬町", "秋葉原", "仲御徒町", "上野", "入谷", "三ノ輪", "南千住", "北千住"),
    "東京メトロ銀座線" ->
      List("渋谷", "表参道", "外苑前", "青山一丁目", "赤坂見附", "溜池山王", "虎ノ門", "新橋", "銀座", "京橋", "日本橋", "三越前",
        "神田", "末広町", "上野広小路", "上野", "稲荷町", "田原町", "浅草"),
    "東京メトロ半蔵門線" ->
      List("渋谷", "表参道", "青山一丁目", "永田町", "半蔵門", "九段下", "神保町", "大手町", "三越前", "水天宮前", "清澄白河",
        "住吉", "錦糸町", "押上"),
    "東京メトロ東西線" ->
      List("中野", "落合", "高田馬場", "早稲田", "神楽坂", "飯田橋", "九段下", "竹橋", "大手町", "日本橋", "茅場町", "門前仲町",
        "木場", "東陽町", "南砂町", "西葛西", "葛西", "浦安", "南行徳", "行徳", "妙典", "原木中山", "西船橋"),
    "東葉高速鉄道" ->
      List("西船橋", "東海神", "飯山満", "北習志野", "船橋日大前", "八千代緑が丘", "八千代中央", "村上", "東葉勝田台"),
    "京成本線" ->
      List("京成上野", "日暮里", "新三河島", "町屋", "千住大橋", "京成関屋", "堀切菖蒲園", "お花茶屋", "青砥", "京成高砂",
        "京成小岩", "江戸川", "国府台", "市川真間", "菅野", "京成八幡", "鬼越", "京成中山", "中山", "京成西船", "海神", "京成船橋",
        "大神宮下", "船橋競馬場", "谷津", "京成津田沼", "京成大久保", "実籾", "八千代台", "京成大和田", "勝田台", "志津",
        "ユーカリが丘", "京成臼井", "京成佐倉", "大佐倉", "京成酒々井", "宗吾参道", "公津の杜", "京成成田", "空港第2ビル", "成田空港"),
    "新京成線" ->
      List("松戸", "上本郷", "新松戸", "みのり台", "八柱", "常磐平", "五香", "元山", "くぬぎ山", "北初富", "新鎌ヶ谷", "初富",
        "鎌ヶ谷大仏", "二和向台", "三咲", "滝不動", "高根公団", "高根木戸", "北習志野", "習志野", "薬園台", "前原", "新津田沼",
        "京成津田沼"),
    "京成千葉線" ->
      List("京成津田沼", "京成幕張本郷", "京成幕張", "検見川", "京成稲毛", "みどり台", "西登戸", "新千葉", "京成千葉", "千葉中央"),
    "千葉都市モノレール1号線" ->
      List("千葉みなと", "市役所前", "千葉", "栄町", "葭川公園", "県庁前"),
    "千葉都市モノレール2号線" ->
      List("千葉みなと", "市役所前", "千葉", "千葉公園", "作草部", "天台", "穴川", "スポーツセンター", "動物公園", "みつわ台",
        "都賀", "桜木", "小倉台", "千城台北", "千城台")
  )
  val connections: List[(StrPair, StrPair)] = List(
    ("JR中央・総武線", "中野") -> ("東京メトロ東西線", "中野"),
    ("JR中央・総武線", "新宿") -> ("JR山手線", "新宿"),
    ("JR中央・総武線", "代々木") -> ("JR山手線", "代々木"),
    ("JR中央・総武線", "飯田橋") -> ("東京メトロ東西線", "飯田橋"),
    ("JR中央・総武線", "御茶ノ水") -> ("東京メトロ千代田線", "新御茶ノ水"),
    ("JR中央・総武線", "秋葉原") -> ("東京メトロ日比谷線", "秋葉原"),
    ("JR中央・総武線", "秋葉原") -> ("JR山手線", "秋葉原"),
    ("JR中央・総武線", "錦糸町") -> ("東京メトロ半蔵門線", "錦糸町"),
    ("JR中央・総武線", "西船橋") -> ("東京メトロ東西線", "西船橋"),
    ("JR中央・総武線", "西船橋") -> ("東葉高速鉄道", "西船橋"),
    ("JR中央・総武線", "津田沼") -> ("新京成線", "新津田沼"),
    ("JR中央・総武線", "幕張本郷") -> ("京成千葉線", "京成幕張本郷"),
    ("JR中央・総武線", "幕張") -> ("京成千葉線", "京成幕張"),
    ("JR中央・総武線", "千葉") -> ("京成千葉線", "京成千葉"),
    ("JR中央・総武線", "千葉") -> ("千葉都市モノレール1号線", "千葉"),
    ("JR中央・総武線", "千葉") -> ("千葉都市モノレール2号線", "千葉"),
    ("JR山手線", "東京") -> ("JR山手線", "有楽町"),
    ("JR山手線", "東京") -> ("東京メトロ東西線", "大手町"),
    ("JR山手線", "東京") -> ("東京メトロ千代田線", "二重橋前"),
    ("JR山手線", "神田") -> ("東京メトロ銀座線", "神田"),
    ("JR山手線", "秋葉原") -> ("東京メトロ日比谷線", "秋葉原"),
    ("JR山手線", "御徒町") -> ("東京メトロ日比谷線", "仲御徒町"),
    ("JR山手線", "御徒町") -> ("東京メトロ銀座線", "上野広小路"),
    ("JR山手線", "上野") -> ("京成本線", "京成上野"),
    ("JR山手線", "西日暮里") -> ("東京メトロ千代田線", "西日暮里"),
    ("JR山手線", "高田馬場") -> ("東京メトロ東西線", "高田馬場"),
    ("JR山手線", "原宿") -> ("東京メトロ千代田線", "明治神宮前"),
    ("JR山手線", "渋谷") -> ("東京メトロ銀座線", "渋谷"),
    ("JR山手線", "渋谷") -> ("東京メトロ半蔵門線", "渋谷"),
    ("JR山手線", "恵比須") -> ("東京メトロ日比谷線", "恵比須"),
    ("JR山手線", "新橋") -> ("東京メトロ銀座線", "新橋"),
    ("JR山手線", "有楽町") -> ("東京メトロ日比谷線", "日比谷"),
    ("JR山手線", "有楽町") -> ("東京メトロ千代田線", "日比谷"),
    ("東京メトロ千代田線", "表参道") -> ("東京メトロ銀座線", "表参道"),
    ("東京メトロ千代田線", "表参道") -> ("東京メトロ半蔵門線", "表参道"),
    ("東京メトロ千代田線", "国会議事堂前") -> ("東京メトロ銀座線", "溜池山王"),
    ("東京メトロ千代田線", "霞ヶ関") -> ("東京メトロ日比谷線", "霞ヶ関"),
    ("東京メトロ千代田線", "日比谷") -> ("東京メトロ日比谷線", "日比谷"),
    ("東京メトロ千代田線", "大手町") -> ("東京メトロ半蔵門線", "大手町"),
    ("東京メトロ千代田線", "大手町") -> ("東京メトロ東西線", "大手町"),
    ("東京メトロ千代田線", "北千住") -> ("東京メトロ日比谷線", "北千住"),
    ("東京メトロ日比谷線", "銀座") -> ("東京メトロ銀座線", "銀座"),
    ("東京メトロ日比谷線", "仲御徒町") -> ("東京メトロ銀座線", "上野広小路"),
    ("東京メトロ日比谷線", "上野") -> ("東京メトロ銀座線", "上野"),
    ("東京メトロ日比谷線", "上野") -> ("京成本線", "京成上野"),
    ("東京メトロ日比谷線", "茅場町") -> ("東京メトロ東西線", "茅場町"),
    ("東京メトロ銀座線", "上野") -> ("京成本線", "京成上野"),
    ("東京メトロ半蔵門線", "水天宮前") -> ("東京メトロ日比谷線", "人形町"),
    ("東京メトロ半蔵門線", "渋谷") -> ("東京メトロ銀座線", "渋谷"),
    ("東京メトロ半蔵門線", "表参道") -> ("東京メトロ銀座線", "表参道"),
    ("東京メトロ半蔵門線", "青山一丁目") -> ("東京メトロ銀座線", "青山一丁目"),
    ("東京メトロ半蔵門線", "永田町") -> ("東京メトロ銀座線", "赤坂見附"),
    ("東京メトロ半蔵門線", "三越前") -> ("東京メトロ銀座線", "三越前"),
    ("東京メトロ半蔵門線", "九段下") -> ("東京メトロ東西線", "九段下"),
    ("東京メトロ半蔵門線", "大手町") -> ("東京メトロ東西線", "大手町"),
    ("東京メトロ東西線", "日本橋") -> ("東京メトロ銀座線", "日本橋"),
    ("東京メトロ東西線", "西船橋") -> ("東葉高速鉄道", "西船橋"),
    ("東葉高速鉄道", "北習志野") -> ("新京成線", "北習志野"),
    ("京成本線", "京成津田沼") -> ("新京成線", "京成津田沼"),
    ("京成本線", "京成津田沼") -> ("京成千葉線", "京成津田沼"),
    ("新京成線", "京成津田沼") -> ("京成千葉線", "京成津田沼"),
    ("京成千葉線", "京成千葉") -> ("千葉都市モノレール1号線", "千葉"),
    ("京成千葉線", "京成千葉") -> ("千葉都市モノレール2号線", "千葉"),
    ("千葉都市モノレール1号線", "千葉みなと") -> ("千葉都市モノレール2号線", "千葉みなと"),
    ("千葉都市モノレール1号線", "市役所前") -> ("千葉都市モノレール2号線", "市役所前"),
    ("千葉都市モノレール1号線", "千葉") -> ("千葉都市モノレール2号線", "千葉"),
  )
  def edgeOfLine(lineName: String)(edge: Edge): Boolean =
    edge.from.value.asInstanceOf[Station].line == lineName &&
      edge.to.value.asInstanceOf[Station].line == lineName
  def line(name: String): Option[Route] = {
    implicit val searcher: BFS.type = BFS
    lines.get(name).
      flatMap(l => stations.route(BFS, Station(name, l.head), Station(name, l.last), edgeOfLine(name)))
  }
  lazy val stations: Board = lines.foldLeft(Board.empty) { case (board, (lineName, sts)) =>
    sts.sliding(2).foldLeft(board) { (acc, xs) =>
      xs match {
        case h :: t :: Nil => acc ~ (Station(lineName, h), Station(lineName, t))
        case _ => acc
      }
    }.pipe { board =>
      connections.foldLeft(board) {
        case (acc, ((l1, s1), (l2, s2))) => acc ~ (Station(l1, s1), Station(l2, s2))
      }
    }
  }
  lazy val stationsTyped: TypedBoard[Station] = lines.foldLeft(TypedBoard.empty[Station]) {
    case (board, (lineName, sts)) =>
      sts.sliding(2).foldLeft(board) { (acc, xs) =>
        xs match {
          case h :: t :: Nil => acc ~ (Station(lineName, h), Station(lineName, t))
          case _ => acc
        }
      }.pipe { board =>
        connections.foldLeft(board) {
          case (acc, ((l1, s1), (l2, s2))) => acc ~ (Station(l1, s1), Station(l2, s2))
        }
      }
  }
}
