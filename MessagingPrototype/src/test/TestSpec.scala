import messaging.{Server, Message}
import org.scalatest.{Matchers, FlatSpec}

/**
 * Created by justusadam on 18/11/14.
 */
class TestSpec extends FlatSpec with Matchers{

  "The sender" should "send a message to the Server" in {
    val sender = new Caterer
    val receiver = new Manager
    val testmessage = "Testmessage"
    Server.addReceiver(receiver)
    sender send (testmessage, receiver.id) should be (right = true)
    receiver.fetchMessages(0).message should be (testmessage)
    receiver.getInbox.fetchNew(0).message should be (testmessage)
    receiver.getInbox.fetchNew(0).readMessage should be (testmessage)
    receiver.getInbox.fetchNew.isEmpty should be (right = true)
  }
}
