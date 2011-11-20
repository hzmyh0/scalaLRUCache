package samples

import org.scalatest.Assertions
import org.junit.Test

import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import hzmyh0.LRUCache
@RunWith(classOf[JUnitRunner])
class LRUCacheTest extends FunSuite with ShouldMatchers {
  test("Added KV should be found") {
    var cache = new LRUCache(3)
    cache.put("a", "a")
    cache.put("b", "b")
    cache.put("c", "c")
    cache.get("a") should be (Some("a"))
    cache.get("b") should be (Some("b"))
    cache.get("c") should be (Some("c"))
  }
  test("NOT Added K should not be found") {
    var cache = new LRUCache(3)
    cache.put("a", "a")
    cache.get("b") should be (None)
  }
  test("Old key should be discarded") {
    var cache = new LRUCache(3)
    cache.put("a", "a")
    cache.put("b", "b")
    cache.put("c", "c")
    cache.put("d", "d")
    cache.get("a") should be (None)
    cache.get("b") should be (Some("b"))
    cache.get("c") should be (Some("c"))
    cache.get("d") should be (Some("d"))
  }
  test("Duplicate key should overwrite value") {
    var cache = new LRUCache(3)
    cache.put("a", "a")
    cache.put("b", "b")
    cache.put("c", "c")
    cache.put("a", "ZZZ")
    cache.put("a", "YYY")
    cache.get("a") should be (Some("YYY"))
    cache.get("b") should be (Some("b"))
    cache.get("c") should be (Some("c"))
  }
  test("referenced key should not be discarded") {
    var cache = new LRUCache(3)
    cache.put("a", "a")
    cache.put("b", "b")
    cache.put("c", "c")
    cache.get("a")
    cache.get("f")
    cache.put("d", "d")
    cache.get("a") should be (Some("a"))
    cache.get("n")
    cache.get("b") should be (None)
    cache.get("c") should be (Some("c"))
    cache.get("d") should be (Some("d"))
  }
  test("cache can be resized") {
    var cache = new LRUCache(3)
    cache.put("a", "a")
    cache.put("b", "b")
    cache.put("c", "c")
    cache.put("d", "d")
    cache.get("a") should be (None)
    cache.getMaxKeys() should be (3)
    cache.resize(4)
    cache.getMaxKeys() should be (4)
    cache.put("e", "e")
    cache.put("f", "f")
    cache.get("c") should be (Some("c"))
    cache.get("d") should be (Some("d"))
    cache.get("e") should be (Some("e"))
    cache.get("f") should be (Some("f"))
  }
  test("after TTL the key should be discarded") {
    var cache = new LRUCache(3, 100)
    cache.put("a", "a")
    Thread.sleep(200)
    cache.get("a") should be (None)
  }
  test("putting after timeout") {
    var cache = new LRUCache(3, 100)
    cache.put("a", "a")
    Thread.sleep(200)
    cache.put("a", "a")
    cache.get("a") should be (Some("a"))
  }
  test("num of elements should decrease after timeout") {
    var cache = new LRUCache(3, 100)
    cache.put("a", "a")
    cache.size() should be (1)
    Thread.sleep(200)
    cache.size() should be (0)
  }
}