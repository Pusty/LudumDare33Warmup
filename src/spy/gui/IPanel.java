package spy.gui;

import java.util.List;

import me.engine.location.Location;
import me.engine.main.MainClass;

public abstract class IPanel
{
	float p1;
	float p2;
	float p3;
	float p4;
	protected IPanel(Location loc, Location size, String text,float p1,float p2,float p3,float p4)
	{
		m_location = loc;
		m_size = size;
		m_szText = text;
		this.p1=p1;
		this.p2=p2;
		this.p3=p3;
		this.p4=p4;
	}
	
	public float getP1(){
		return p1;
	}
	public float getP2(){
		return p2;
	}
	
	public float getP3(){
		return p3;
	}
	
	public float getP4(){
		return p4;
	}
	
	
	public void PaintBackground()
	{
		
	}
	
	public void Paint()
	{
		
	}
	
	public void OnClick(MainClass m,float mx,float mz){}

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
	
	public Location GetAbsoluteLocation()
	{
		Location ret = new Location(0,0);
		
		for(IPanel parent = m_parent;parent!=null;parent=parent.m_parent)
		{
			ret.x += parent.m_location.x + parent.m_padding1.x;
			ret.z += parent.m_location.z + parent.m_padding1.z;
		}
		
		return ret;
	}
	
	public Location GetAbsoluteSize()
	{
		return m_size;
	}
	
	public void AddChildren(IPanel panel)
	{
		if(panel != null)
		{
			if(m_children == null)
			{
				m_children = m_last = panel;
				return;
			}
			else
			{
				m_last.m_next = panel;
				m_last = panel;
			}
		}
	}
	
	public Location m_location;
	public Location m_size;
	public Location m_padding1;
	public Location m_padding2;
	public String m_szText;

	public IPanel m_next;
	public IPanel m_first;
	public IPanel m_parent;
	public IPanel m_children;
	public IPanel m_last;
	
	public void tick(){
		
	}
}
