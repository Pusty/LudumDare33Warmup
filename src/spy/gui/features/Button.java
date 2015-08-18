package spy.gui.features;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glEnd;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import me.engine.location.Location;
import me.engine.main.MainClass;
import me.engine.render.Render2D;
import spy.gui.*;
import spy.gui.features.*;

public class Button extends IPanel
{

	public Button(Location loc, Location size, String text,float p1,float p2,float p3,float p4) 
	{
		super(loc, size, text,p1,p2,p3,p4);

	}
	
	public void PaintBackground()
	{
//		Render2D.setColor(m_backColor.getRed(), m_backColor.getGreen(), m_backColor.getBlue());
//		Render2D.FillBox(GetAbsoluteLocation().x, GetAbsoluteLocation().z, GetAbsoluteSize().x, GetAbsoluteSize().z);

//		Render2D.setColor(m_foreColor.getRed(), m_foreColor.getGreen(), m_foreColor.getBlue());
//		Render2D.DrawBox(GetAbsoluteLocation().x, GetAbsoluteLocation().z, GetAbsoluteSize().x, GetAbsoluteSize().z);
	}

	public void Paint()
	{
			GL11.glPushMatrix();
			GL11.glTranslatef(m_location.x+0.5f, m_location.z+0.5f, 0f);
			if(timer<0)
				glBindTexture(GL_TEXTURE_2D,
						MainClass.classForRender.getPictureLoader().getImageAsInteger("item_0"));
			else
				glBindTexture(GL_TEXTURE_2D,
						MainClass.classForRender.getPictureLoader().getImageAsInteger("item_8"));
			glBegin(GL_QUADS);
			GL11.glTexCoord2f(0f, 1f);
			GL11.glVertex2f(0f,m_size.z);
			GL11.glTexCoord2f(1f, 1f);
			GL11.glVertex2f(m_size.x,m_size.z);
			GL11.glTexCoord2f(1f, 0f);
			GL11.glVertex2f(m_size.x,0f);
			GL11.glTexCoord2f(0f, 0f);
			GL11.glVertex2f(0f,0f);	
			glEnd(); 
			
			GL11.glTranslatef(m_size.x/2 - this.m_szText.length()/2f,0.5f,0f);
			Render2D.renderString(MainClass.classForRender, m_szText);
			GL11.glPopMatrix();
	
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
	int timer=0;
	public void OnClick(MainClass m,float mx,float mz) {
		if(timer>=0){}
		else{
		buttonClick(m,mx,mz);
		timer=20;
		}
	}
	public void tick(){
		if(timer>=0)timer--;
	}
	public void buttonClick(MainClass m,float mx,float mz){
		
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


