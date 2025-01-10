package io.lemonlabs.uri.inet

import scala.scalanative._
import scala.scalanative.unsafe._
import scala.scalanative.libc._

@link("idn2")
@extern
object idn2 {
  def idn2_to_ascii_8z(input: CString, output: Ptr[CString], flags: CInt): CInt = extern
}
