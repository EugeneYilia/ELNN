package com.jstarcraft.module.similarity;

import com.jstarcraft.module.similarity.DiceCoefficientSimilarity;
import com.jstarcraft.module.similarity.Similarity;

public class DiceCoefficientSimilarityTestCase extends AbstractSimilarityTestCase {

	@Override
	protected boolean checkCorrelation(float correlation) {
		return correlation < 1.00001F;
	}

	@Override
	protected float getIdentical() {
		return 1F;
	}
	
	@Override
	protected Similarity getSimilarity() {
		return new DiceCoefficientSimilarity();
	}

}
