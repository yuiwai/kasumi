package scala.util

object chaining {
  implicit class Wrap[A](v: A) {
    def pipe[B](f: A => B): B = f(v)
  }
}

