package io.lemonlabs.uri.inet

import io.lemonlabs.uri.inet.PunycodeSupport._

import scala.scalanative._
import scala.scalanative.unsafe._

trait PunycodeSupport {
  def toPunycode(host: String): String = {
    val output: Ptr[CString] = stackalloc[CString]()
    val (result, error) = Zone.acquire { implicit z =>
      val error = idn2.idn2_to_ascii_8z(input = toCString(host), output = output, flags = IDN2_TRANSITIONAL)
      val result = fromCString(!output)
      (result, error)
    }
    libc.stdlib.free(!output)
    if (error < 0) {
      throw Idn2Exception(host, IDN2_TRANSITIONAL, error)
    } else {
      result
    }
  }
}

object PunycodeSupport {
  case class Idn2Exception(input: String, flags: Int, error: Int)
      extends RuntimeException(
        s"idn2_to_ascii_8z for input '$input' and flags $flags returned error $error."
      )
  val IDN2_TRANSITIONAL: CInt = 4
}
