package common.commercial

import com.gu.commercial.branding._
import common.Edition
import common.Edition.defaultEdition
import play.api.libs.json.Json.JsValueWrapper
import play.api.libs.json.Reads._
import play.api.libs.json._

// todo: move into commercial-shared
object NewBranding {

  implicit val dimensionsFormat = Json.format[Dimensions]

  implicit val logoFormat = Json.format[Logo]

  implicit val brandingTypeWrites: Writes[BrandingType] = new Writes[BrandingType] {
    def writes(brandingType: BrandingType): JsValue = Json.obj("name" -> brandingType.name)
  }

  implicit val brandingTypeReads: Reads[BrandingType] = {
    (__ \ "name").read[String] map fromName
  }

  def fromName(name: String): BrandingType = name match {
    case PaidContent.name => PaidContent
    case Foundation.name => Foundation
    case _ => Sponsored
  }

  implicit val brandingFormat = Json.format[Branding]

  class MapEditionReads[T]()(implicit reads: Reads[T]) extends Reads[Map[Edition, T]] {
    def reads(jv: JsValue): JsResult[Map[Edition, T]] =
      JsSuccess(jv.as[Map[String, T]].map { case (editionId, t) =>
        Edition.byId(editionId).getOrElse(defaultEdition) -> t.asInstanceOf[T]
      })
  }

  class MapEditionWrites[T]()(implicit writes: Writes[T]) extends Writes[Map[Edition, T]] {
    def writes(map: Map[Edition, T]): JsValue =
      Json.obj(map.map { case (edition, t) =>
        val ret: (String, JsValueWrapper) = edition.id -> Json.toJson(t)
        ret
      }.toSeq: _*)
  }

  class MapEditionFormats[T]()(implicit format: Format[T]) extends Format[Map[Edition, T]] {
    override def reads(json: JsValue): JsResult[Map[Edition, T]] = new MapEditionReads[T].reads(json)
    override def writes(o: Map[Edition, T]): JsValue = new MapEditionWrites[T].writes(o)
  }

  implicit val brandingOptWrites: Writes[Option[Branding]] = new Writes[Option[com.gu
  .commercial.branding.Branding]] {
    def writes(branding: Option[Branding]): JsValue = Json.obj("branding" -> branding)
  }

  implicit val brandingOptReads: Reads[Option[Branding]] = {
    (__ \ "branding").readNullable[Branding]
  }

  implicit val editionBrandingFormat = Json.format[EditionBranding]
}
