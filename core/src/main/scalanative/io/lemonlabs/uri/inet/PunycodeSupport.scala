package io.lemonlabs.uri.inet

import io.lemonlabs.uri.inet.PunycodeSupport._

import scala.scalanative._
import scala.scalanative.unsafe._

trait PunycodeSupport {
  def toPunycode(host: String): String = {
    val (result, error) = Zone.acquire { implicit z =>
      val output: Ptr[CString] = stackalloc[CString]()
      val error = idn2.idn2_to_ascii_8z(input = toCString(host), output = output, flags = IDN2_TRANSITIONAL)
      val result = fromCString(!output)
      libc.stdlib.free(!output)
      (result, error)
    }
    if (error < 0) {
      throw new RuntimeException(
        s"idn2_to_ascii_8z for input '$host' and flags IDN2_TRANSITIONAL returned error $error."
      )
    } else {
      result
    }
  }
}

object PunycodeSupport {
  val IDN2_TRANSITIONAL: CInt = 4
}
