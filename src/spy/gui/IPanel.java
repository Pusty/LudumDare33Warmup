package spy.gui;

import me.engine.location.Location;

public abstract class IPanel
{
	protected IPanel(Location loc, Location size, String text)
	{
		m_location = loc;
		m_size = size;
		m_szText = text;
	}
	
	public void Paint()
	{
		
	}

	public void LeftMouseDown(Mouse mouse)
	{
		
	}
	
	public void LeftMouseUp(Mouse mouse)
	{
		//comment
	}
	
	public void RightMouseDown(Mouse mouse)
	{
		
	}
	
	public void RightMouseUp(Mouse mouse)
	{
		
	}
	
	public void MouseMove(Mouse mouse)
	{
		
	}
	
	public Location m_location;
	public Location m_size;
	public String m_szText;
}
