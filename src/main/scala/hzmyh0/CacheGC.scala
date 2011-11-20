package hzmyh0

class CacheGC(lru_cache_ : LRUCache) extends Thread {
	override def run() {
	  if (lru_cache_.getTimeToLive() == 0) {
	    return
	  }
	  lru_cache_.gc_loop()
	}
}