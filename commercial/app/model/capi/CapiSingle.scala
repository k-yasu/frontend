package commercial.model.capi

import common.Edition
import model.ContentType
import play.api.libs.json.{Json, Writes}
import common.commercial.NewBranding._

case class CapiSingle(
  articleHeadline: String,
  articleUrl: String,
  articleText: Option[String],
  articleImage: ImageInfo,
  audioTag: Boolean,
  galleryTag: Boolean,
  videoTag: Boolean,
  branding: Option[com.gu.commercial.branding.Branding],
  edition: String
)

object CapiSingle {

  def fromContent(
    contentType: ContentType,
    edition: Edition,
    noArticles: Int = 1): CapiSingle = {

    val content = contentType.content
    val branding = contentType.metadata.editionBrandings(edition)
    val imageInfo = CapiImages.buildImageData(content.trail.trailPicture, noArticles)

    CapiSingle(content.trail.headline, content.metadata.webUrl, content.trail.fields.trailText, imageInfo, content.tags.isAudio,
      content.tags.isGallery, content.tags.isVideo, branding, edition.id)
  }
  implicit val writesCapiSingle: Writes[CapiSingle] = Json.writes[CapiSingle]
}
