package io.luckynumbers

import messages.Result
import services.GameSimulator
import services.ResultsCalculator
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class GameSimulatorSpec extends AnyWordSpec with Matchers {

  "GameSimulator" should {
    "return two results for two players" in {

      // Custom number generator for 2 players and a bot
      val generateNumber: Int => (Int, Int) = {
        case 0 => (0, 42)
        case 1 => (1, 123)
        case 2 => (2, 999)
        case _ => (-1, -1)
      }

      val results: List[Result] = GameSimulator.simulateGame(2, Some(generateNumber), None)

      results.head shouldEqual Result(1, 2, 999, 900)
      results.tail.head shouldEqual Result(2, 1, 123, 6)
      results.size shouldEqual 2
    }
  }

  "GameSimulator" should {
    "return no results for three players when bot has the best result" in {

      // Custom number generator for 2 players and a bot
      val generateNumber: Int => (Int, Int) = {
        case 0 => (0, 1111)
        case 1 => (1, 123)
        case 2 => (2, 999)
        case 3 => (2, 222)
        case _ => (-1, -1)
      }

      val results: List[Result] = GameSimulator.simulateGame(3, Some(generateNumber), None)

      results.size shouldEqual 0
    }
  }

  "GameSimulator" should {
    "return no results for 0 players" in {
      val results: List[Result] = GameSimulator.simulateGame(0, None, None)

      results.size shouldEqual 0
    }
  }

  "GameSimulator" should {
    "return three results for five players when bot has the 4th best result" in {

      // Custom number generator for 2 players and a bot
      val generateNumber: Int => (Int, Int) = {
        case 0 => (0, 777)
        case 1 => (1, 123)
        case 2 => (2, 456)
        case 3 => (2, 888)
        case 4 => (2, 999)
        case 5 => (2, 1111)
        case _ => (-1, -1)
      }

      val results: List[Result] = GameSimulator.simulateGame(5, Some(generateNumber), None)

      results.size shouldEqual 3
    }
  }
}
