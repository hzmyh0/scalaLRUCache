package samples
import hzmyh0.LRUCache

class CacheThread(lru_cache_ : LRUCache, sleep_time_ : Int, num_loop_ : Int) extends Thread {
	override def run() {
	  var i = 0
	  while (i < num_loop_) {
	    val key = (sleep_time_ + i).toString()
	    lru_cache_.put(key, "a")
	    Thread.sleep(sleep_time_)
	    lru_cache_.get(key)
	    i += 1
	  }
	}
}