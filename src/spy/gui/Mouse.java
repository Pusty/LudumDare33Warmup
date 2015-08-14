package spy.gui;

import me.engine.location.Location;

public class Mouse 
{
	public Location m_location;
	
	public boolean At(float x, float y, float w, float h)
	{
		return (m_location.x >= x && m_location.z >= y && m_location.x <= x+w && m_location.z <= y+h);
	}
	
	public boolean At(Location loc, Location sze)
	{
		return At(loc.x,loc.z,sze.x,sze.z);
	}
}
