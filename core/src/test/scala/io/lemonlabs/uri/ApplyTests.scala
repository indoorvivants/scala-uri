package io.lemonlabs.uri

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ApplyTests extends AnyFlatSpec with Matchers {
  "Url apply method" should "accept scheme, host and path" in {
    val url = Url(scheme = "http", host = "theon.github.com", path = "/blah")
    url shouldBe an[AbsoluteUrl]
    url.schemeOption should equal(Some("http"))
    url.hostOption should equal(Some(DomainName("theon.github.com")))
    url.path.toString() should equal("/blah")
    url.query should equal(QueryString.empty)
  }

  it should "accept scheme and host" in {
    val url = Url(scheme = "http", host = "example.com")
    url shouldBe an[AbsoluteUrl]
    url.schemeOption should equal(Some("http"))
    url.hostOption should equal(Some(DomainName("example.com")))
    url.path.toString() should equal("")
    url.query should equal(QueryString.empty)
  }

  it should "accept host and path" in {
    val url = Url(host = "example.com", path = "/example")
    url shouldBe an[ProtocolRelativeUrl]
    url.schemeOption should equal(None)
    url.hostOption should equal(Some(DomainName("example.com")))
    url.path.toString() should equal("/example")
    url.query should equal(QueryString.empty)
  }

  it should "accept scheme and path" in {
    val url = Url(scheme = "mailto", path = "example@example.com")
    url shouldBe an[UrlWithoutAuthority]
    url.schemeOption should equal(Some("mailto"))
    url.hostOption should equal(None)
    url.path.toString() should equal("example@example.com")
    url.query should equal(QueryString.empty)
  }

  it should "accept QueryString" in {
    val qs = QueryString.fromPairs("testKey" -> "testVal")
    val url = Url(query = qs)
    url shouldBe an[RelativeUrl]
    url.schemeOption should equal(None)
    url.user should equal(None)
    url.password should equal(None)
    url.port should equal(None)
    url.hostOption should equal(None)
    url.query should equal(qs)
  }

  it should "accept scheme, host and QueryString" in {
    val qs = QueryString.fromPairs("testKey" -> "testVal")
    val url = Url(scheme = "http", host = "theon.github.com", query = qs)
    url shouldBe an[AbsoluteUrl]
    url.schemeOption should equal(Some("http"))
    url.hostOption should equal(Some(DomainName("theon.github.com")))
    url.query should equal(qs)
  }

  it should "automatically add a slash when missing if authority is specified" in {
    val url = Url(host = "example.com", path = "example")
    url.path.toString() should equal("/example")
  }

  "Urn apply method" should "construct from nid and nss" in {
    val urn = Urn("nid", "nss:nss:nss")
    urn.nid should equal("nid")
    urn.nss should equal("nss:nss:nss")
    urn.toString() should equal("urn:nid:nss:nss:nss")
  }
}
