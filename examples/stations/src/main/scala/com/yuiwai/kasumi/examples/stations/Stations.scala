package com.yuiwai.kasumi.examples.stations

import com.yuiwai.kasumi.core.implementation._
import scala.util.chaining._

final case class Line(mark: String, organization: String, name: String)
final case class Station(line: Set[Line], number: Int, name: String)
object Station {
  def apply(line: Line, number: Int, name: String): Station = apply(Set(line), number, name)
}
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
  val KS_1 = Line("KS", "京成電鉄", "本線")
  val KS_2 = Line("KS", "京成電鉄", "成田空港線")
  val KS_3 = Line("KS", "京成電鉄", "東成田線")
  val KS_4 = Line("KS", "京成電鉄", "押上線")
  val KS_5 = Line("KS", "京成電鉄", "金町線")
  val KS_6 = Line("KS", "京成電鉄", "千葉線")
  val KS_7 = Line("KS", "京成電鉄", "千原線")
  val SL = Line("SL", "新京成電鉄", "新京成線")
  val TR = Line("TR", "東葉高速鉄道", "東葉高速線")
  val CM_1 = Line("CM", "千葉都市モノレール", "千葉都市モノレール1号線")
  val CM_2 = Line("CM", "千葉都市モノレール", "千葉都市モノレール2号線")

  val all = Set(
    JB, JY, G, H, T, C, Y, Z, N, F, KS_1, KS_2, KS_3, KS_4, KS_5, KS_6, KS_7, SL, TR, CM_1, CM_2
  )
}
object Stations {
  import Lines._

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

  val allJB = Set(
    JB01,
    JB02,
    JB03,
    JB04,
    JB05,
    JB06,
    JB07,
    JB08,
    JB09,
    JB10,
    JB11,
    JB12,
    JB13,
    JB14,
    JB15,
    JB16,
    JB17,
    JB20,
    JB21,
    JB22,
    JB23,
    JB24,
    JB25,
    JB26,
    JB27,
    JB30,
    JB31,
    JB32,
    JB33,
    JB34,
    JB35,
    JB36,
    JB37,
    JB38,
    JB39
  )

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

  val allJY = Set(
    JY01,
    JY02,
    JY03,
    JY04,
    JY05,
    JY06,
    JY07,
    JY08,
    JY09,
    JY10,
    JY11,
    JY12,
    JY13,
    JY14,
    JY15,
    JY16,
    JY17,
    JY18,
    JY19,
    JY20,
    JY21,
    JY22,
    JY23,
    JY24,
    JY25,
    JY27,
    JY28,
    JY29,
    JY30
  )

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

  val allG = Set(
    G01,
    G02,
    G03,
    G04,
    G05,
    G06,
    G07,
    G08,
    G09,
    G10,
    G11,
    G12,
    G13,
    G14,
    G15,
    G16,
    G17,
    G18,
    G19
  )

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

  val allH = Set(
    H01,
    H02,
    H03,
    H04,
    H05,
    H06,
    H07,
    H08,
    H09,
    H10,
    H11,
    H12,
    H13,
    H14,
    H15,
    H16,
    H17,
    H18,
    H19,
    H20,
    H21
  )

  val T01 = Station(T, 1, "中野")
  val T02 = Station(T, 2, "落合")
  val T03 = Station(T, 3, "高田馬場")
  val T04 = Station(T, 4, "早稲田")
  val T05 = Station(T, 5, "神楽坂")
  val T06 = Station(T, 6, "飯田橋")
  val T07 = Station(T, 7, "九段下")
  val T08 = Station(T, 8, "竹橋")
  val T09 = Station(T, 9, "大手町")
  val T10 = Station(T, 10, "日本橋")
  val T11 = Station(T, 11, "茅場町")
  val T12 = Station(T, 12, "門前仲町")
  val T13 = Station(T, 13, "木場")
  val T14 = Station(T, 14, "東陽町")
  val T15 = Station(T, 15, "南砂町")
  val T16 = Station(T, 16, "西葛西")
  val T17 = Station(T, 17, "葛西")
  val T18 = Station(T, 18, "浦安")
  val T19 = Station(T, 19, "南行徳")
  val T20 = Station(T, 10, "行徳")
  val T21 = Station(T, 21, "妙典")
  val T22 = Station(T, 22, "原木中山")
  val T23 = Station(T, 23, "西船橋")

  val allT = Set(
    T01,
    T02,
    T03,
    T04,
    T05,
    T06,
    T07,
    T08,
    T09,
    T10,
    T11,
    T12,
    T13,
    T14,
    T15,
    T16,
    T17,
    T18,
    T19,
    T20,
    T21,
    T22,
    T23
  )

  val C01 = Station(C, 1, "代々木上原")
  val C02 = Station(C, 2, "代々木公園")
  val C03 = Station(C, 3, "明治神宮前")
  val C04 = Station(C, 4, "表参道")
  val C05 = Station(C, 5, "乃木坂")
  val C06 = Station(C, 6, "赤坂")
  val C07 = Station(C, 7, "国会議事堂前")
  val C08 = Station(C, 8, "霞ヶ関")
  val C09 = Station(C, 9, "日比谷")
  val C10 = Station(C, 10, "二重橋前")
  val C11 = Station(C, 11, "大手町")
  val C12 = Station(C, 12, "新御茶ノ水")
  val C13 = Station(C, 13, "湯島")
  val C14 = Station(C, 14, "根津")
  val C15 = Station(C, 15, "千駄木")
  val C16 = Station(C, 16, "西日暮里")
  val C17 = Station(C, 17, "町屋")
  val C18 = Station(C, 18, "北千住")
  val C19 = Station(C, 19, "綾瀬")
  val C20 = Station(C, 20, "北綾瀬")

  val allC = Set(
    C01,
    C02,
    C03,
    C04,
    C05,
    C06,
    C07,
    C08,
    C09,
    C10,
    C11,
    C12,
    C13,
    C14,
    C15,
    C16,
    C17,
    C18,
    C19,
    C20
  )

  // TODO Y 有楽町線

  val Z01 = Station(Z, 1, "渋谷")
  val Z02 = Station(Z, 2, "表参道")
  val Z03 = Station(Z, 3, "青山一丁目")
  val Z04 = Station(Z, 4, "永田町")
  val Z05 = Station(Z, 5, "半蔵門")
  val Z06 = Station(Z, 6, "九段下")
  val Z07 = Station(Z, 7, "神保町")
  val Z08 = Station(Z, 8, "大手町")
  val Z09 = Station(Z, 9, "三越前")
  val Z10 = Station(Z, 10, "水天宮前")
  val Z11 = Station(Z, 11, "清澄白河")
  val Z12 = Station(Z, 12, "住吉")
  val Z13 = Station(Z, 13, "錦糸町")
  val Z14 = Station(Z, 14, "押上")

  val allZ = Set(
    Z01,
    Z02,
    Z03,
    Z04,
    Z05,
    Z06,
    Z07,
    Z08,
    Z09,
    Z10,
    Z11,
    Z12,
    Z13,
    Z14
  )

  val KS01 = Station(KS_1, 1, "京成上野")
  val KS02 = Station(KS_1, 2, "日暮里")
  val KS03 = Station(KS_1, 3, "新三河島")
  val KS04 = Station(KS_1, 4, "町屋")
  val KS05 = Station(KS_1, 5, "千住大橋")
  val KS06 = Station(KS_1, 6, "京成関屋")
  val KS07 = Station(KS_1, 7, "堀切菖蒲園")
  val KS08 = Station(KS_1, 8, "お花茶屋")
  val KS09 = Station(KS_1, 9, "青砥")
  val KS10 = Station(KS_1, 10, "京成高砂")
  val KS11 = Station(KS_1, 11, "京成小岩")
  val KS12 = Station(KS_1, 12, "江戸川")
  val KS13 = Station(KS_1, 13, "国府台")
  val KS14 = Station(KS_1, 14, "市川真間")
  val KS15 = Station(KS_1, 15, "菅野")
  val KS16 = Station(KS_1, 16, "京成八幡")
  val KS17 = Station(KS_1, 17, "鬼越")
  val KS18 = Station(KS_1, 18, "京成中山")
  val KS19 = Station(KS_1, 19, "中山")
  val KS20 = Station(KS_1, 20, "京成西船")
  val KS21 = Station(KS_1, 21, "海神")
  val KS22 = Station(KS_1, 22, "京成船橋")
  val KS23 = Station(KS_1, 23, "大神宮下")
  val KS24 = Station(KS_1, 24, "船橋競馬場")
  val KS25 = Station(KS_1, 25, "谷津")
  val KS26 = Station(Set(KS_1, KS_6), 26, "京成津田沼")
  val KS27 = Station(KS_1, 27, "京成大久保")
  val KS28 = Station(KS_1, 28, "実籾")
  val KS29 = Station(KS_1, 29, "八千代台")
  val KS30 = Station(KS_1, 30, "京成大和田")
  val KS31 = Station(KS_1, 31, "勝田台")
  val KS32 = Station(KS_1, 32, "志津")
  val KS33 = Station(KS_1, 33, "ユーカリが丘")
  val KS34 = Station(KS_1, 34, "京成臼井")
  val KS35 = Station(KS_1, 35, "京成佐倉")
  val KS36 = Station(KS_1, 36, "大佐倉")
  val KS37 = Station(KS_1, 37, "京成酒々井")
  val KS38 = Station(KS_1, 38, "宗吾参道")
  val KS39 = Station(KS_1, 39, "公津の杜")
  val KS40 = Station(KS_1, 30, "京成成田")
  val KS41 = Station(KS_1, 41, "空港第2ビル")
  val KS42 = Station(KS_1, 42, "成田空港")

  val allKS_1 = Set(
    KS01,
    KS02,
    KS03,
    KS04,
    KS05,
    KS06,
    KS07,
    KS08,
    KS09,
    KS10,
    KS11,
    KS12,
    KS13,
    KS14,
    KS15,
    KS16,
    KS17,
    KS18,
    KS19,
    KS20,
    KS21,
    KS22,
    KS23,
    KS24,
    KS25,
    KS26,
    KS27,
    KS28,
    KS29,
    KS30,
    KS31,
    KS32,
    KS33,
    KS34,
    KS35,
    KS36,
    KS37,
    KS38,
    KS39,
    KS40,
    KS41,
    KS42
  )

  val KS52 = Station(KS_6, 52, "京成幕張本郷")
  val KS53 = Station(KS_6, 53, "京成幕張")
  val KS54 = Station(KS_6, 54, "検見川")
  val KS55 = Station(KS_6, 55, "京成稲毛")
  val KS56 = Station(KS_6, 56, "みどり台")
  val KS57 = Station(KS_6, 57, "西登戸")
  val KS58 = Station(KS_6, 58, "新千葉")
  val KS59 = Station(KS_6, 59, "京成千葉")
  val KS60 = Station(KS_6, 60, "千葉中央")

  val allKS_6 = Set(
    KS52,
    KS53,
    KS54,
    KS55,
    KS56,
    KS57,
    KS58,
    KS59,
    KS60
  )

  val SL01 = Station(SL, 1, "松戸")
  val SL02 = Station(SL, 2, "上本郷")
  val SL03 = Station(SL, 3, "新松戸")
  val SL04 = Station(SL, 4, "みのり台")
  val SL05 = Station(SL, 5, "八柱")
  val SL06 = Station(SL, 6, "常磐平")
  val SL07 = Station(SL, 7, "五香")
  val SL08 = Station(SL, 8, "元山")
  val SL09 = Station(SL, 9, "くぬぎ山")
  val SL10 = Station(SL, 10, "北初富")
  val SL11 = Station(SL, 11, "新鎌ヶ谷")
  val SL12 = Station(SL, 12, "初富")
  val SL13 = Station(SL, 13, "鎌ヶ谷大仏")
  val SL14 = Station(SL, 14, "二和向台")
  val SL15 = Station(SL, 15, "三咲")
  val SL16 = Station(SL, 16, "滝不動")
  val SL17 = Station(SL, 17, "高根公団")
  val SL18 = Station(SL, 18, "高根木戸")
  val SL19 = Station(SL, 19, "北習志野")
  val SL20 = Station(SL, 20, "習志野")
  val SL21 = Station(SL, 21, "薬園台")
  val SL22 = Station(SL, 22, "前原")
  val SL23 = Station(SL, 23, "新津田沼")
  val SL24 = Station(SL, 24, "京成津田沼")

  val allSL = Set(
    SL01,
    SL02,
    SL03,
    SL04,
    SL05,
    SL06,
    SL07,
    SL08,
    SL09,
    SL10,
    SL11,
    SL12,
    SL13,
    SL14,
    SL15,
    SL16,
    SL17,
    SL18,
    SL19,
    SL20,
    SL21,
    SL22,
    SL23,
    SL24
  )

  val TR01 = Station(TR, 1, "西船橋")
  val TR02 = Station(TR, 2, "東海神")
  val TR03 = Station(TR, 3, "飯山満")
  val TR04 = Station(TR, 4, "北習志野")
  val TR05 = Station(TR, 5, "船橋日大前")
  val TR06 = Station(TR, 6, "八千代緑が丘")
  val TR07 = Station(TR, 7, "八千代中央")
  val TR08 = Station(TR, 8, "村上")
  val TR09 = Station(TR, 9, "東葉勝田台")

  val allTR = Set(
    TR01,
    TR02,
    TR03,
    TR04,
    TR05,
    TR06,
    TR07,
    TR08,
    TR09
  )

  val CM01 = Station(Set(CM_1, CM_2), 1, "千葉みなと")
  val CM02 = Station(Set(CM_1, CM_2), 2, "市役所前")
  val CM03 = Station(Set(CM_1, CM_2), 3, "千葉")
  val CM16 = Station(CM_1, 16, "栄町")
  val CM17 = Station(CM_1, 17, "葭川公園")
  val CM18 = Station(CM_1, 18, "県庁前")

  val allCM_1 = Set(
    CM01,
    CM02,
    CM03,
    CM16,
    CM17,
    CM18
  )

  val CM04 = Station(CM_2, 4, "千葉公園")
  val CM05 = Station(CM_2, 5, "作草部")
  val CM06 = Station(CM_2, 6, "天台")
  val CM07 = Station(CM_2, 7, "穴川")
  val CM08 = Station(CM_2, 8, "スポーツセンター")
  val CM09 = Station(CM_2, 9, "動物公園")
  val CM10 = Station(CM_2, 10, "みつわ台")
  val CM11 = Station(CM_2, 11, "都賀")
  val CM12 = Station(CM_2, 12, "桜木")
  val CM13 = Station(CM_2, 13, "小倉台")
  val CM14 = Station(CM_2, 14, "千城台北")
  val CM15 = Station(CM_2, 15, "千城台")

  val allCM_2 = Set(
    CM01,
    CM02,
    CM03,
    CM04,
    CM05,
    CM06,
    CM07,
    CM08,
    CM09,
    CM10,
    CM11,
    CM12,
    CM13,
    CM14,
    CM15
  )

  val all: Set[Station] =
    allJB ++ allJY ++ allG ++ allH ++ allT ++ allC ++ allZ ++ allKS_1 ++ allKS_6 ++ allTR ++ allCM_1 ++ allCM_2

  val junctions: Seq[Set[Station]] = Seq(
    // 中野
    Set(JB07, T01),

    // 新宿
    Set(JB10, JY17),

    // 代々木
    Set(JB11, JY18),

    // 飯田橋
    Set(JB16, T06),

    // 御茶ノ水
    Set(JB18, C12),

    // 秋葉原
    Set(JB19, JY03, H15),

    // 錦糸町
    Set(JB22, Z13),

    // 西船橋
    Set(JB30, T23, TR01),

    // 津田沼
    Set(JB33, SL23),

    // 幕張本郷
    Set(JB34, KS52),

    // 幕張
    Set(JB35, KS53),

    // 千葉
    Set(JB39, KS59, CM03),

    // 東京
    Set(JY01, JY30, T09, C10),

    // 神田
    Set(JY02, G13),

    // 御徒町
    Set(JY04, G15, H16),

    // 上野
    Set(JY05, G16, H17, KS01),

    // 日暮里
    Set(JY07, KS02),

    // 西日暮里
    Set(JY08, C16),

    // 高田馬場
    Set(JY15, T03),

    // 原宿
    Set(JY19, C03),

    // 渋谷
    Set(JY20, G01, Z01),

    // 恵比寿
    Set(JY21, H02),

    // 新橋
    Set(JY29, G08),

    // 有楽町
    Set(JY30, H07, C09),

    // 表参道
    Set(C04, G02, Z02),

    // 国会議事堂前
    Set(C07, G06),

    // 霞ヶ関
    Set(C08, H06),

    // 大手町
    Set(C11, Z08, T09),

    // 北千住
    Set(C18, H21),

    // 銀座
    Set(H08, G09),

    // 茅場町
    Set(H12, T11),

    // 水天宮前
    Set(Z10, H13),

    // 青山一丁目
    Set(Z03, G04),

    // 永田町
    Set(Z04, G05),

    // 三越前
    Set(Z09, G12),

    // 九段下
    Set(Z06, T07),

    // 日本橋
    Set(T10, G11),

    // 北習志野
    Set(TR04, SL19)
  )
}

object Data {
  type StrPair = (String, String)
  def lineByName(name: String): Option[Line] = lines.keys.find(_.name == name)
  def lineAsRouteByName(name: String): Option[TypedRoute[Station]] = {
    def pairAsEdges(xs: List[Station], current: List[TypedEdge[Station]]): Seq[TypedEdge[Station]] = xs match {
      case first :: second :: rest => pairAsEdges(second :: rest, TypedEdge(first, second) :: current)
      case _ => current.reverse
    }
    lines.find(_._1.name == name).
      flatMap {
        case (_, first :: second :: rest) =>
          Some(TypedRoute(TypedEdge(first, second), pairAsEdges(second :: rest, Nil)))
        case _ => None
      }
  }
  lazy val lines: Map[Line, List[Station]] = Lines.all.foldLeft(Map.empty[Line, List[Station]]) {
    (acc, line) =>
      Stations.all.filter(_.line.contains(line)).toList.sortBy(_.number).pipe(xs => acc.updated(line, xs))
  }
  lazy val stations: TypedBoard[Station] = lines.foldLeft(TypedBoard.empty[Station]) {
    case (board, (line, sts)) =>
      sts.sliding(2).foldLeft(board) { (acc, xs) =>
        xs match {
          case h :: t :: Nil => acc ~ (h, t)
          case _ => acc
        }
      }.pipe { board =>
        Stations.junctions.foldLeft(board) {
          (acc, xs) => connectJunctions(acc, xs.toList)
        }
      }
  }
  private def connectJunctions(board: TypedBoard[Station], xs: List[Station]): TypedBoard[Station] = {
    xs match {
      case Nil => board
      case _ :: Nil => board
      case h1 :: h2 :: t => connectJunctions(board ~ (h1, h2), h2 :: t)
    }
  }
}
