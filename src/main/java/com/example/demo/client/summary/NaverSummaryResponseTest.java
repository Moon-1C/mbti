package com.example.demo.client.summary;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class NaverSummaryResponseTest {

	@Test
	void 네이버요약테스트() {
		String message =
				"일반적으로 충치 예방을 위해 칫솔질을 꼼꼼히 해야 했지만, 이러한 노력에도 불구하고 때로는 불가피한 상황이 발생하기도 합니다. 식습관이나 유전적 요인, 환경적 요소에 따라 상황이 종종 발생할 수 있습니다. 많은 충치가 발생한다면 치아의 표면 상태가 심각한 상황이 많습니다. 또한 침의 분비가 적고 점도가 높은 특징도 있습니다.\n"
						+ "또한 치아의 가장 바깥쪽에 위치하는 법랑질에서도 유전적으로 불균형한 상태가 있을 수 있습니다. 이러한 상태에서는 치아가 우식될 위험이 매우 높아졌습니다. 이런 구조라면 초기에 관리를 철저히 해주는 것이 좋은 방법입니다. 질환이 있음에도 불구하고 방치한다면 상황은 점점 악화될 수 있습니다. 이럴 때에는 접근 방식과 치유 방법을 변경해야 했기 때문에 매우 주의해야 합니다.\n";

		SummaryResponse summaryResponse = new NaverSummaryResponse();
		String response = summaryResponse.getResponse(message);
		System.out.println(response);
	}
}
