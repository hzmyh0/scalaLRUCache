package hzmyh0

import java.util.Calendar

class LRUCache(num_max_keys : Int, val time_to_live_ : Long) {
    // time_to_live_ == 0: 時間経過によるキャッシュ破棄をしない
    def this(num_max_keys : Int) = this(num_max_keys, 0)
    var num_max_keys_ = num_max_keys
	var cache_map_ = Map[String, String]()
	var history_ = List[Element]()
	val cache_gc_ = new CacheGC(this).start()
	
	def getTimeToLive() = time_to_live_
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
	def put(key : String, value : String) = synchronized {
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
	def get(key : String) : Option[String] = synchronized {
	  if (keyExists(key)) {
	    history_ = (history_ - new Element(key)) ::: List(new Element(key))
	  }
	  cache_map_.get(key)
	}
	def gc_loop() {
	  while(true) {
		  val wait_time = do_gc()
		  if (wait_time >= 0) {
		    Thread.sleep(wait_time)
		  }
	  	}  
	}
	def do_gc() : Long = synchronized {
	  var wait_time : Long = 0
	  if (history_.size == 0) {
	    return time_to_live_
	  }
	  val key = history_.head.key_
	  val time_to_live = history_(history_.indexOf(new Element(key))).refered_time_.getTime() + time_to_live_
	  wait_time = time_to_live - Calendar.getInstance().getTime().getTime()
	  if (wait_time <= 0){
        history_ -= new Element(key)
        cache_map_ -= key
	  }
	  return wait_time
	}
}