package ft

/**
  * Created by vladkanash on 2/12/16.
  */

import scala.math.{atan2, cos, sin, sqrt}

abstract class Complex extends Equals {
  val real:Double
  val imag:Double
  val magnitude:Double
  val angle:Double

  def + (other:Complex) = RectComplex(real + other.real, imag + other.imag)
  def - (other:Complex) = RectComplex(real - other.real, imag - other.imag)
  def * (other:Complex) =
    Complex(real * other.real - imag * other.imag, real * other.imag + imag * other.real)
  def / (other:Complex) =
    PolarComplex(magnitude / other.magnitude, angle - other.angle)
  def conjugate = RectComplex(real, -imag)
  def * = conjugate
  def unary_- = RectComplex(-real, -imag)

  override def toString = "("+real+", "+imag+" ["+magnitude+"e"+angle+"theta])"

  override def equals (other:Any) = {
    other match {
      case that: Complex =>
        that.canEqual(this) && real == that.real && imag == that.imag
      case _ =>
        false
    }
  }

  override def canEqual(other:Any) = {
    other.isInstanceOf[Complex]
  }

  override def hashCode = {
    41 * (41 + real.toInt) + imag.toInt
  }
}

class RectComplex(val real:Double, val imag:Double) extends Complex {
  val magnitude = sqrt(real*real + imag*imag)
  val angle:Double = atan2(imag, real)
}

class PolarComplex(val magnitude:Double, val angle:Double) extends Complex {
  val real = magnitude * cos(angle)
  val imag = magnitude * sin(angle)
}

object Complex {
  // Default to the more-common rectangular version
  def apply(real:Double, imag:Double) = new RectComplex(real, imag)
}

object RectComplex {
  def apply(real:Double, imag:Double) = {
    new RectComplex(real, imag)
  }
}

object PolarComplex {
  def apply(magnitude:Double, angle:Double) = {
    new PolarComplex(magnitude, angle)
  }
}

package object util {
  implicit def double2complex(real:Double):Complex = RectComplex(real, 0)
}
