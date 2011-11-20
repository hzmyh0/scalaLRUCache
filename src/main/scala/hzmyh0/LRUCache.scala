package hzmyh0

import java.util.Calendar

class LRUCache(num_max_keys : Int, time_to_live_ : Long) {
    // time_to_live_ == 0: 時間経過によるキャッシュ破棄をしない
    def this(num_max_keys : Int) = this(num_max_keys, 0)
    var num_max_keys_ = num_max_keys
	var cache_map_ = Map[String, String]()
	var history_ = List[Element]()
	
	def size() = history_.size
	def getMaxKeys() = num_max_keys_
	def resize(new_num_keys : Int) = {
      num_max_keys_ = new_num_keys
    }
	def keyExists(key : String) = {
	  if (cache_map_.get(key) != None) 
	    true
	    else false
	}
	def put(key : String, value : String) = {
	  if (keyExists(key)) {
	    cache_map_ += (key -> value)
	    history_ = (history_ - new Element(key)) ::: List(new Element(key))
	  }
	  else {
		  if (history_.length >= num_max_keys_) {
		    cache_map_ -= history_.head.key_
		    history_ = history_.tail
		  }
		  cache_map_ += (key -> value)
		  history_ = history_ ::: List(new Element(key))
	  }
	}
	def get(key : String) : Option[String] = {
	  discardIfTimeOut(key)
	  if (keyExists(key)) {
	    history_ = (history_ - new Element(key)) ::: List(new Element(key))
	  }
	  cache_map_.get(key)
	}
	def discardIfTimeOut(key : String) : Unit = {
	  if (time_to_live_ == 0 ) return
	  if (keyExists(key)) {
	      val time_to_live = history_(history_.indexOf(new Element(key))).refered_time_.getTime() + time_to_live_
	      if (time_to_live < Calendar.getInstance().getTime().getTime()) {
	        history_ -= new Element(key)
	        cache_map_ -= key
	      }
	  }
	}
}