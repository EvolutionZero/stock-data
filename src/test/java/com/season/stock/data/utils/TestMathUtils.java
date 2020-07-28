package com.season.stock.data.utils;


import org.junit.Assert;
import org.junit.Test;

public class TestMathUtils {

	@Test
	public void testIncrease(){
		double[] datas = new double[]{1,2,3,2,4,2};
		Assert.assertTrue(MathUtils.isIncrease(datas));
	}
	
	@Test
	public void testDecrease(){
		double[] datas = new double[]{6,5,2,3,4,5,1};
		Assert.assertTrue(MathUtils.isDecrease(datas));
	}
}
