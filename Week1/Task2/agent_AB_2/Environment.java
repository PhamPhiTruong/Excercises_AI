package agent_AB_2; 

public class Environment {
	public static final Action MOVE_LEFT = new DynamicAction("LEFT");
	public static final Action MOVE_RIGHT = new DynamicAction("RIGHT");
	public static final Action SUCK_DIRT = new DynamicAction("SUCK");
	public static final Action MOVE_UP = new DynamicAction("UP");
	public static final Action MOVE_DOWN = new DynamicAction("DOWN");
	public static final String LOCATION_A = "A";
	public static final String LOCATION_B = "B";
	public static final String LOCATION_C = "C";
	public static final String LOCATION_D = "D";
	private String[][] arrTest = new String[2][2];
	private int x=0;//toa do cua o theo hang doc
	private int y=0;//toa do cua o theo hang ngang
	private int score;
	public enum LocationState {
		CLEAN, DIRTY
	}

	private EnvironmentState envState;
	private boolean isDone = false;// all squares are CLEAN
	private Agent agent = null;

	public Environment(LocationState locAState, LocationState locBState
			,LocationState locCState,LocationState locDState) {
		envState = new EnvironmentState(locAState, locBState,locCState,locDState);
	}
	

	// add an agent into the enviroment
	public void addAgent(Agent agent, String location) {
		this.agent=agent;
		envState.setAgentLocation(location);
	}

	public EnvironmentState getCurrentState() {
		return this.envState;
	}

	// Update enviroment state when agent do an action
	public EnvironmentState executeAction(Action action) {
		arrTest[0][0] = LOCATION_A;
		arrTest[0][1] = LOCATION_B;
		arrTest[1][0] = LOCATION_C;
		arrTest[1][1] = LOCATION_D;
		for(int i=0;i<arrTest.length;i++) {
			boolean flag = false;
			for(int j=0;j<arrTest[i].length;j++) {
				if(arrTest[i][j].equals(envState.getAgentLocation())) {
					x =i;
					y =j;
					flag=true;
					break;
				}
			}
			if(flag) break;
		}
		
		int tempx = x;
		int tempy = y;
		if(action.equals(SUCK_DIRT)) envState.setLocationState(envState.getAgentLocation(), LocationState.CLEAN);
		if(action.equals(MOVE_LEFT)) {
			y-=1;
		}else if(action.equals(MOVE_RIGHT)){
			y+=1;
		}else if(action.equals(MOVE_UP)) {
			x-=1;
		}else if(action.equals(MOVE_DOWN)) {
			x+=1;
		}
		
		if(x>=arrTest.length||x<0||y<0||y>=arrTest.length) {
			x= tempx;
			y= tempy;
		}else {
			envState.setAgentLocation(arrTest[x][y]);
			envState.setLocationState(arrTest[x][y], envState.getLocationState(arrTest[x][y]));
			return envState;
		}
		return null;
		
	}

	// get percept<AgentLocation, LocationState> at the current location where agent
	// is in.
	public Percept getPerceptSeenBy() {
		return new Percept(envState.getAgentLocation(), envState.getLocationState(envState.getAgentLocation()));
	}

	public void step() {
		envState.display();
		System.out.println("Score : "+this.score);
		if(this.isDone) {
			System.out.println("Job is done");
			return;
		}
		String agentLocation = this.envState.getAgentLocation();
		Action anAction = agent.execute(getPerceptSeenBy());
		EnvironmentState es = executeAction(anAction);
		boolean flag =true;
		while(flag) {
			if(es!=null) {
				if(anAction.equals(SUCK_DIRT)) {
					score+=500;
				}else if(anAction.equals(NoOpAction.NO_OP)) {
					score-=100;
				}else {
					score-=10;
				}
				flag=false;
			}else {
				anAction = agent.execute(getPerceptSeenBy());
				es = executeAction(anAction);
			}
		}

		System.out.println("Agent Loc.: " + agentLocation + "\tAction: " + anAction);

		if ((es.getLocationState(LOCATION_A) == LocationState.CLEAN)
				&& (es.getLocationState(LOCATION_B) == LocationState.CLEAN)
				&& (es.getLocationState(LOCATION_C) == LocationState.CLEAN)
				&& (es.getLocationState(LOCATION_D) == LocationState.CLEAN))
			isDone = true;// if both squares are clean, then agent do not need to do any action
		es.display();
	}

	public void step(int n) {
		for (int i = 0; i < n; i++) {
			step();
			System.out.println("-------------------------");
		}
	}

	public void stepUntilDone() {
		int i = 0;

		while (!isDone) {
			System.out.println("step: " + i++);
			step();
		}
	}
}
