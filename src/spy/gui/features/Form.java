package spy.gui.features;

import me.engine.location.Location;
import me.engine.main.MainClass;
import me.engine.render.Render2D;
import spy.gui.*;

public class Form extends IPanel
{

	public Form(Location loc, Location size, String text,int in) 
	{
		super(loc, size, text,0,0,0,0);
		parts = new IPanel[in];
	}
	
	public void PaintBackground()
	{
		Render2D.setColor(100, 100, 100);
		Render2D.FillBox(GetAbsoluteLocation().x, GetAbsoluteLocation().z, GetAbsoluteSize().x, GetAbsoluteSize().z);
		Render2D.setColor(255,255,255);
		Render2D.DrawBox(GetAbsoluteLocation().x, GetAbsoluteLocation().z, GetAbsoluteSize().x, GetAbsoluteSize().z);
		Render2D.DrawBox(GetAbsoluteLocation().x, GetAbsoluteLocation().z, GetAbsoluteSize().x, GetTitlebarSize().z);
	}

	public void Paint()
	{
//		Render2D.DrawText(new Location(GetAbsoluteLocation().x + 5.f, GetAbsoluteLocation().z + GetTitlebarSize().z / 2), m_szText);
		
//		Render2D.BeginClip(GetAbsoluteLocation().x+ m_padding1.x, GetAbsoluteLocation().z+m_padding1.z, GetTitlebarSize().x - (m_padding1.x+m_padding2.x), GetTitlebarSize().z - (m_padding1.z+m_padding2.z));

		if(m_children != null)
			m_children.Paint();
		
//		Render2D.EndClip();
		
		if(m_next != null)
		{
			m_next.PaintBackground();
			m_next.Paint();
		}
	}

	public void LeftMouseDown(Mouse mouse)
	{
		if(mouse.At(GetTitlebarLocation(), GetTitlebarSize()))
		{
			m_moveFrom.x = mouse.m_location.x - GetTitlebarLocation().x;
			m_moveFrom.z = mouse.m_location.z - GetTitlebarLocation().z;
			
			m_bMoving = true;
		}
	}	
	public void LeftMouseUp(Mouse mouse)
	{
		m_bMoving = false;
	}
	
	public void RightMouseDown(Mouse mouse)
	{
		
	}
	
	public void RightMouseUp(Mouse mouse)
	{
		
	}
	
	public void MouseMove(Mouse mouse)
	{
		if(m_bMoving)
		{
			m_location.x = mouse.m_location.x - m_location.x;
			m_location.z = mouse.m_location.z - m_location.z;
		}
	}

	public Location GetTitlebarLocation()
	{
		return GetAbsoluteLocation();
	}
	
	public Location GetTitlebarSize()
	{
		return new Location(GetAbsoluteSize().x, 22.f);
	}
	
	protected boolean m_bMoving;
	protected Location m_moveFrom;
	
	
	IPanel[] parts;

	public int size(){return parts.length;}
	public void setGuiPart(int in,IPanel p){
		parts[in]=p;
	}
	public IPanel getGuiPart(int in){
		return parts[in];
	}
	
	public void tick(){
		for(int in=0;in<size();in++){
			if(parts[in]==null)continue;
				parts[in].tick();
		}
	}
	
	public void click(MainClass m,float mx,float my){
		float x = mx/m.getWidth()*100f;
		float y = my/m.getHeight()*100f;
		for(int in=0;in<size();in++){
			if(parts[in]==null)continue;
			if(y>=parts[in].getP1() && y <= parts[in].getP2()
			&& x>=parts[in].getP3() && x <= parts[in].getP4())
				parts[in].OnClick(m,x,y);
		}
	}
}

