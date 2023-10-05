package agent_AB_2;

import agent_AB_2.Environment.LocationState;

public class AgentProgram {

	public Action execute(Percept p) {// location, status
		if(p.getLocationState().equals(LocationState.DIRTY)) return Environment.SUCK_DIRT;
		if(p.getLocationState().equals(LocationState.CLEAN)) {
			double random = Math.random();
			random=random*4;
			int toDo = (int) random;
			switch (toDo) {
			case 0:
				return Environment.MOVE_LEFT;
			case 1:
				return Environment.MOVE_RIGHT;
			case 2:
				return Environment.MOVE_UP;
			case 3:
				return Environment.MOVE_DOWN;
			default:
				break;
			}
		}
		return NoOpAction.NO_OP;
		
	}
}