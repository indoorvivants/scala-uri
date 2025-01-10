package io.lemonlabs.uri.inet

import io.lemonlabs.uri.inet.PunycodeSupport._

import scala.scalanative._
import scala.scalanative.unsafe._

trait PunycodeSupport {
  def toPunycode(host: String): String = Zone.acquire { implicit z =>
    val output: Ptr[CString] = stackalloc[CString]()
    val error = idn2.idn2_to_ascii_8z(input = toCString(host), output = output, flags = IDN2_TRANSITIONAL)
    val result = fromCString(!output)
    libc.stdlib.free(!output)
    result
  }
}

object PunycodeSupport {
  val IDN2_TRANSITIONAL: CInt = 4
}
