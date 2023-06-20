package streamingapi.client;

/**
 * Monitor used to stop the execution or to check if the execution should continue.
 */
public class StreamMonitor {

	private boolean isActive = true;

	/**
	 * Stops the monitored execution
	 */
	public void stopExecution() {

		this.isActive = false;
	}

	/**
	 * Indicates if the monitored execution should continue its execution.
	 *
	 * @return The monitor status.
	 */
	public boolean shouldContinue() {

		return isActive;
	}
}
