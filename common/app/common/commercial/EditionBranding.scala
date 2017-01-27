package common.commercial

import com.gu.commercial.branding.{Branding, BrandingFinder}
import com.gu.contentapi.client.model.v1.{Content, Section, Tag}
import common.Edition

case class EditionBranding(edition: Edition, branding: Option[Branding])

object EditionBranding {

  def fromItem(item: Content): Seq[EditionBranding] = Edition.all.map { edition =>
    EditionBranding(edition, BrandingFinder.findBranding(item, edition.id))
  }

  // todo
  //  def fromItems(items: Seq[Content]): Map[Edition, Option[Branding]] = Edition.all.map { edition =>
  //    edition -> BrandingFinder.findBranding(items, edition.id)
  //  }.toMap

  def fromSection(section: Section): Seq[EditionBranding] = Edition.all.map { edition =>
    EditionBranding(edition, BrandingFinder.findBranding(section, edition.id))
  }

  def fromTag(tag: Tag): Seq[EditionBranding] = Edition.all.map { edition =>
    EditionBranding(edition, BrandingFinder.findBranding(tag, edition.id))
  }
}
