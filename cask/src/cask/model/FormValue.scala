package cask.model

object FormValue{
  def fromUndertow(from: io.undertow.server.handlers.form.FormData.FormValue) = {
    if (!from.isFile) Plain(from.getValue, from.getHeaders)
    else File(from.getValue, from.getFileName, from.getPath, from.getHeaders)
  }
  case class Plain(value: String,
                   headers: io.undertow.util.HeaderMap) extends FormValue

  case class File(value: String,
                  fileName: String,
                  filePath: java.nio.file.Path,
                  headers: io.undertow.util.HeaderMap) extends FormValue
}
sealed trait FormValue{
  def value: String
  def headers: io.undertow.util.HeaderMap
  def asFile: Option[FormValue.File] = this match{
    case p: FormValue.Plain => None
    case p: FormValue.File => Some(p)
  }
}