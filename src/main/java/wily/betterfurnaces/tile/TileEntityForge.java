package wily.betterfurnaces.tile;

public class TileEntityForge extends TileEntitySmeltingBase {
	//Constants
	@Override
	protected int getDefaultCookTime() {
		return 4;
	}
	public int FUEL() {return 3;}
	public int[] UPGRADES(){ return new int[] {7, 8, 9, 10, 11, 12,13};}
	public int HEATER() {return 10;}
	public int FINPUT(){ return INPUTS()[0];}
	public int LINPUT(){ return INPUTS()[INPUTS().length - 1];}
	public int FOUTPUT(){ return OUTPUTS()[0];}
	public int LOUTPUT(){ return OUTPUTS()[OUTPUTS().length - 1];}
	public int[] INPUTS(){ return new int[]{0,1,2};}
	public int[] OUTPUTS(){ return new int[]{4,5,6};}
	public int LiquidCapacity() {return 8000;}
	public int MAX_FE_TRANSFER(){ return 14000;}
	public int MAX_ENERGY_STORED(){ return 64000;}
	public int inventorySize(){ return 14;}
    @Override
	public boolean canSmelts(){
		return canSmelt(FINPUT(), FOUTPUT()) || canSmelt(FINPUT() + 1, FOUTPUT() + 1) || canSmelt(FINPUT() + 2, FOUTPUT() + 2);
	}
	@Override
	public void trySmelt(){
		if (canSmelt(FINPUT(), FOUTPUT())) smeltItem( FINPUT(), FOUTPUT());
		if (canSmelt(FINPUT() + 1, FOUTPUT() + 1)) smeltItem( FINPUT() + 1, FOUTPUT() + 1);
		if (canSmelt(FINPUT() + 2, FOUTPUT() + 2)) smeltItem( FINPUT() + 2, FOUTPUT() + 2);
	}
}