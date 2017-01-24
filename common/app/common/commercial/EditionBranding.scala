package common.commercial

import common.Edition

case class EditionBranding(edition: Edition, branding: Option[com.gu.commercial.branding.Branding])

object EditionBranding {

  def fromFindBranding(findBranding: Edition => Option[com.gu.commercial.branding.Branding]): Map[Edition, Option[com.gu.commercial.branding
  .Branding]] = {
    Edition.all.map { edition =>
      edition -> findBranding(edition)
    }.toMap
  }
}
