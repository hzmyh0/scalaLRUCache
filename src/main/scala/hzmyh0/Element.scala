package hzmyh0

import java.util.Calendar
import java.util.Date

class Element(val key_ : String) {
	var refered_time_ = updateTime()
	override def hashCode() = key_.hashCode()
	override def equals(other : Any)  = other match {
	  case that : Element =>
	    this.key_ == that.key_ &&
	    this.getClass == that.getClass
	}
	def updateTime() : Date = {
	  refered_time_ = Calendar.getInstance().getTime()
	  refered_time_
	}
}