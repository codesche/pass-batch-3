package com.fastcampus.pass;

import com.fastcampus.pass.repository.ptpackage.PackageEntity;
import com.fastcampus.pass.repository.ptpackage.PackageRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@ActiveProfiles("test")
class PassBatchApplicationTests {

	@Autowired
	private PackageRepository packageRepository;

	@DisplayName("test_save")
	@Test
	public void test_save() {
	    // given (사전 준비)
		PackageEntity packageEntity = new PackageEntity();
		packageEntity.setPackageName("바디 챌린지 PT 12주");
		packageEntity.setPeriod(84);

	    // when (테스트 진행할 행위)
		packageRepository.save(packageEntity);

	    // then (행위에 대한 결과 검증)
		assertNotNull(packageEntity.getPackageSeq());

	}

	@DisplayName("test_findByCreatedAtAfter")
	@Test
	public void test_findByCreatedAtAfter() {
	    // given (사전 준비)
		LocalDateTime dateTime = LocalDateTime.now().minusMinutes(1);

		PackageEntity packageEntity0 = new PackageEntity();
		packageEntity0.setPackageName("학생 전용 3개월");
		packageEntity0.setPeriod(90);
		packageRepository.save(packageEntity0);

		PackageEntity packageEntity1 = new PackageEntity();
		packageEntity1.setPackageName("학생 전용 6개월");
		packageEntity1.setPeriod(180);
		packageRepository.save(packageEntity1);

	    // when (테스트 진행할 행위)
		final List<PackageEntity> packageEntities = packageRepository.findByCreatedAtAfter(dateTime, PageRequest.of(
				0, 1, Sort.by("packageSeq").descending()));

	    // then (행위에 대한 결과 검증)
		assertEquals(1, packageEntities.size());
		assertEquals(packageEntity1.getPackageSeq(), packageEntities.get(0).getPackageSeq());
	}

	@DisplayName("test_updateCountAndPeriod")
	@Test
	public void test_updateCountAndPeriod() {
	    // given (사전 준비)
		PackageEntity packageEntity = new PackageEntity();
		packageEntity.setPackageName("바디프로필 이벤트 4개월");
		packageEntity.setPeriod(90);
		packageRepository.save(packageEntity);

	    // when (테스트 진행할 행위)
		int updatedCount = packageRepository.updateCountAndPeriod(packageEntity.getPackageSeq(), 30, 120);
		final PackageEntity updatedPackageEntity = packageRepository.findById(packageEntity.getPackageSeq()).get();

	    // then (행위에 대한 결과 검증)
		assertEquals(1, updatedCount);
		assertEquals(30, updatedPackageEntity.getCount());
		assertEquals(120, updatedPackageEntity.getPeriod());
	}

	@DisplayName("test_delete")
	@Test
	public void testMethod() {
	    // given (사전 준비)
		PackageEntity packageEntity = new PackageEntity();
		packageEntity.setPackageName("제거할 이용권");
		packageEntity.setCount(1);
		PackageEntity newPackageEntity = packageRepository.save(packageEntity);

	    // when (테스트 진행할 행위)
		packageRepository.deleteById(newPackageEntity.getPackageSeq());

	    // then (행위에 대한 결과 검증)
		assertTrue(packageRepository.findById(newPackageEntity.getPackageSeq()).isEmpty());

	}

}
