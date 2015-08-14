package me.engine.util;

import me.engine.entity.Entity;

public class Sorter {
	public Sorter(){
		
	}
	public static Object[] sort(Object[] value,float[] messure,Object start,float startfloat){
			Object[] values = new Object[value.length+1];
			float[] valuesMessure = new float[value.length+1];
			for(int i=0;i<value.length;i++){
				values[i]=value[i];
				valuesMessure[i]=messure[i];
			}
			values[values.length-1]=start;
			valuesMessure[values.length-1]=startfloat;
			float val;Object va;
			for(int x=0;x<values.length;x++)
			for (int a=0;a<values.length;a++){
				for(int b=a;b<values.length;b++){
					if(valuesMessure[a] > valuesMessure[b]){
					val = valuesMessure[a];
					va = values[a];
					valuesMessure[a]=valuesMessure[b];
					valuesMessure[b]=val;
					values[a]=values[b];
					values[b]=va;
					}
				}
			}
			Object[] arrays = new Object[2];
			arrays[0]=values;
			arrays[1]=valuesMessure;
			return arrays;
	}
	
	public static Object[] sortEntitys(Entity[] entitys,float playerZ){
		int amount=0;
		for(int a=0;a<entitys.length;a++)
			if(entitys[a]!=null)amount++;
		
		int[] values = new int[amount+1];
		float[] valuesMessure = new float[amount+1];
		int index=0;
		for(int i=0;i<entitys.length;i++){
			if(entitys[i]==null)continue;
			values[index]=i;
			valuesMessure[index]=entitys[i].getZ();
			index++;
		}
		values[values.length-1]=-5;
		valuesMessure[values.length-1]=playerZ;
		float val;int va;
		for(int x=0;x<values.length;x++)
		for (int a=0;a<values.length;a++){
			for(int b=a;b<values.length;b++){
				if(valuesMessure[a] > valuesMessure[b]){
				val = valuesMessure[a];
				va = values[a];
				valuesMessure[a]=valuesMessure[b];
				valuesMessure[b]=val;
				values[a]=values[b];
				values[b]=va;
				}
			}
		}
//		for(int i=0;i<values.length;i++)
//			System.out.println(values[i]+":"+valuesMessure[i]);
		Object[] arrays = new Object[2];
		arrays[0]=values;
		arrays[1]=valuesMessure;
		return arrays;
}
	/*public static void main(String[] args){
		Object[] obj = {0,1,2,3};
		float[] floats = {7f,5f,2f,3.5f};
		new Sorter().sort(obj,floats,-5,2.2f);
	}*/
}
