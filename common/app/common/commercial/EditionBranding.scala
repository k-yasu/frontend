package common.commercial

import com.gu.commercial.branding.{Branding, BrandingFinder}
import com.gu.contentapi.client.model.v1.Section
import common.Edition

case class EditionBranding(edition: Edition, branding: Option[Branding])

object EditionBranding {

  def fromSection(section: Section): Map[Edition, Option[Branding]] = Edition.all.map { edition =>
    edition -> BrandingFinder.findBranding(section, edition.id)
  }.toMap
}
