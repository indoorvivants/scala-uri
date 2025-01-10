package io.lemonlabs.uri.inet

import io.lemonlabs.uri.inet.PunycodeSupport._

import scala.scalanative.unsafe._

trait PunycodeSupport {
  def toPunycode(host: String): String = Zone.acquire { implicit z =>
    val result: Ptr[CString] = stackalloc[CString]()
    val error = idn2.idn2_to_ascii_8z(toCString(host), result, IDN2_TRANSITIONAL)
    fromCString(!result)
  }
}

object PunycodeSupport {
  val IDN2_TRANSITIONAL: CInt = 4
}
