package spy.gui.features;

import java.awt.Color;

import me.engine.location.Location;
import me.engine.render.Render2D;
import spy.gui.*;
import spy.gui.features.*;

public class Label extends IPanel
{

	Label(Location loc, Location size, String text) 
	{
		super(loc, size, text,0,0,0,0);

	}
	
	public void PaintBackground()
	{
	}

	public void Paint()
	{
		Render2D.DrawText(new Location(GetAbsoluteLocation().x + 5.f, GetAbsoluteLocation().z + GetAbsoluteSize().z / 2), m_szText);
		
		if(m_next != null)
		{
			m_next.PaintBackground();
			m_next.Paint();
		}
	}
	
	public void LeftMouseDown(Mouse mouse)
	{
	}	
	public void LeftMouseUp(Mouse mouse)
	{
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

	public void OnClick()
	{
		
	}
	
	protected Color m_foreColor;
	protected Color m_backColor;
	protected InnerMouseState_e m_eInnerState;
}


