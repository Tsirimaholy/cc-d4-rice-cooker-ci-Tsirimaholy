import org.junit.jupiter.api.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals

class RiceCookerTest {

    @Test
    fun testCookRice() {
        val cooker = RiceCooker()
        val latch = CountDownLatch(1)

        cooker.plugIn()
        cooker.addWater()
        cooker.cookRice()

        // Wait for the asynchronous operation to complete
        latch.await(5, TimeUnit.SECONDS)

        assertEquals(RiceCookerState.IDLE, cooker.state)
    }

    @Test
    fun testWarmRice() {
        val cooker = RiceCooker()
        val latch = CountDownLatch(1)

        cooker.plugIn()
        cooker.addWater()
        cooker.warmRice()

        // Wait for the asynchronous operation to complete
        latch.await(2, TimeUnit.SECONDS)

        assertEquals(RiceCookerState.IDLE, cooker.state)
    }

    @Test
    fun testCancel() {
        val cooker = RiceCooker()

        cooker.plugIn()

        cooker.addWater()

        cooker.cookRice() // Start cooking
        cooker.cancel()
        assertEquals(RiceCookerState.IDLE, cooker.state)
    }

    @Test
    fun testAddWater() {
        val cooker = RiceCooker()
        cooker.addWater()

        assertEquals(true, cooker.hasWater)
    }

    @Test
    fun testPlugIn() {
        val cooker = RiceCooker()
        cooker.plugIn()

        assertEquals(true, cooker.isPluggedIn)
    }

    @Test
    fun testUnplug() {
        val cooker = RiceCooker()
        cooker.plugIn()
        cooker.addWater()
        cooker.cookRice() // Start cooking
        cooker.unplug()

        // Unplug while cooking should not change the state
        assertEquals(RiceCookerState.COOKING, cooker.state)

        // Wait for the asynchronous cooking operation to complete
        val latch = CountDownLatch(1)
        latch.await(5, TimeUnit.SECONDS)

        // Now, unplug again
        cooker.unplug()
        assertEquals(RiceCookerState.IDLE, cooker.state)
    }
}
