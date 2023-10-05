package agent_AB_1;

public class AgentProgram {

	public Action execute(Percept p) {// location, status
		if(p.getLocationState().equals(Environment.LocationState.DIRTY)) return Environment.SUCK_DIRT;
		if(p.getAgentLocation().equals(Environment.LOCATION_A)) return Environment.MOVE_RIGHT;
		if(p.getAgentLocation().equals(Environment.LOCATION_B)) return Environment.MOVE_LEFT;
		
		return NoOpAction.NO_OP;
		
	}
}