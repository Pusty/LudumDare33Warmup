package spy.gui.features;

import java.awt.Color;

import me.engine.location.Location;
import me.engine.render.Render2D;
import spy.gui.*;
import spy.gui.features.*;

public class Button extends IPanel
{

	Button(Location loc, Location size, String text) 
	{
		super(loc, size, text);

	}
	
	public void PaintBackground()
	{
		Render2D.setColor(m_backColor.getRed(), m_backColor.getGreen(), m_backColor.getBlue());
		Render2D.FillBox(GetAbsoluteLocation().x, GetAbsoluteLocation().z, GetAbsoluteSize().x, GetAbsoluteSize().z);

		Render2D.setColor(m_foreColor.getRed(), m_foreColor.getGreen(), m_foreColor.getBlue());
		Render2D.DrawBox(GetAbsoluteLocation().x, GetAbsoluteLocation().z, GetAbsoluteSize().x, GetAbsoluteSize().z);
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
		if(mouse.At(GetAbsoluteLocation(), GetAbsoluteSize()))
		{
			m_eInnerState = InnerMouseState_e.Pressed;
			UpdateColors();
		}
	}	
	public void LeftMouseUp(Mouse mouse)
	{
		m_eInnerState= mouse.At(GetAbsoluteLocation(), GetAbsoluteSize()) ? InnerMouseState_e.Hover : InnerMouseState_e.None;
		UpdateColors();
	}
	
	public void RightMouseDown(Mouse mouse)
	{
		
	}
	
	public void RightMouseUp(Mouse mouse)
	{
		
	}
	
	public void MouseMove(Mouse mouse)
	{
		if(mouse.At(GetAbsoluteLocation(),GetAbsoluteSize()) && m_eInnerState != InnerMouseState_e.Pressed)
		{
			m_eInnerState = InnerMouseState_e.Hover;
			UpdateColors();
		}
		else
		{
			m_eInnerState = InnerMouseState_e.None;
			UpdateColors();
		}
	}

	public void OnClick()
	{
		
	}
	
	public void UpdateColors()
	{
		if(m_eInnerState == InnerMouseState_e.None)
		{
			m_backColor = new Color(100,100,100,255);
			m_foreColor = new Color(255,255,255,255);
		}
		if(m_eInnerState == InnerMouseState_e.Hover)
		{
			m_backColor = new Color(150,150,150,255);
			m_foreColor = new Color(255,255,255,255);
		}
		if(m_eInnerState == InnerMouseState_e.Pressed)
		{
			m_backColor = new Color(200,200,200,255);
			m_foreColor = new Color(80,80,80,255);
		}
	}
	
	protected Color m_foreColor;
	protected Color m_backColor;
	protected InnerMouseState_e m_eInnerState;
}


