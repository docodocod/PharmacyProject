package com.example.pharmacyproject.pharmacy.repository

import com.example.pharmacyproject.AbstractIntegrationContainerBaseTest
import com.example.pharmacyproject.pharmacy.entity.Pharmacy
import com.example.pharmacyproject.pharmacy.service.PharmacyRepositoryService
import org.springframework.beans.factory.annotation.Autowired

import java.time.LocalDateTime


class PharmacyRepositoryTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private PharmacyRepository pharmacyRepository

    @Autowired
    private PharmacyRepositoryService pharmacyRepositoryService

    def setup(){ //테스트케이스 시작전에 디비를 비워줘야 한다.
        pharmacyRepository.deleteAll()
    }

    def "PharmacyRepository save"() {

        given:
        String address = "서울 특별시 성북구 종암동"
        String name = "은혜 약국"
        double latitude = 36.11
        double longitude = 128.11

        def pharmacy = Pharmacy.builder()
                .pharmacyAddress(address)
                .pharmacyName(name)
                .latitude(latitude)
                .longitude(longitude)
                .build()

        when:
        def result=pharmacyRepository.save(pharmacy)

        then:
        result.getPharmacyAddress()==address
        result.getPharmacyName()==name
        result.getLatitude()==latitude
        result.getLongitude()==longitude
    }

    def "PharmacyRepository saveAll"(){
        given:
        String address = "서울 특별시 성북구 종암동"
        String name = "은혜 약국"
        double latitude = 36.11
        double longitude = 128.11

        def pharmacy = Pharmacy.builder()
                .pharmacyAddress(address)
                .pharmacyName(name)
                .latitude(latitude)
                .longitude(longitude)
                .build()
        when:
        pharmacyRepository.saveAll(Arrays.asList((pharmacy)))
        def result=pharmacyRepository.findAll()

        then:
        result.size()==1
    }

    def "BaseTimeEntity insert"() {

        given:
        def now = LocalDateTime.now()
        String address = "서울 특별시 성북구 종암동"
        String name = "은혜 약국"

        def pharmacy = Pharmacy.builder()
                .pharmacyAddress(address)
                .pharmacyName(name)
                .build()
        when:
        pharmacyRepository.save(pharmacy)
        def result = pharmacyRepository.findAll()
        then:
        result.get(0).getCreatedDate().isAfter(now)
        result.get(0).getModifiedDate().isAfter(now)
    }

    def "self invocation"(){

        given:
        String address="서울 특별시 성북구 종암동"
        String name="은혜 약국"
        double latitude=36.11
        double longitude=128.11

        def pharmacy=Pharmacy.builder()
            .pharmacyAddress(address)
            .pharmacyName(name)
            .latitude(latitude)
            .longitude(longitude)
            .build()

        when:
        pharmacyRepositoryService.bar(Arrays.asList(pharmacy))

        then:
        def e= thrown(RuntimeException.class)
        def result=pharmacyRepositoryService.findAll()
        result.size()==1 //트랜잭션이 적용 되지 않는다(롤백 적용 x)
    }

    def "PharmacyRepository update - dirty checking fail"(){
        given:
        String inputAddress="서울 특별시 성북구 종암동"
        String modifiedAddress="서울 광진구 구의동"
        String name="은혜 약국"

        def pharmacy=Pharmacy.builder()
            .pharmacyAddress(inputAddress)
            .pharmacyName(name)
            .build()

        when:
        def entity=pharmacyRepository.save(pharmacy)
        pharmacyRepositoryService.updateAddressWithoutTransaction(entity.getId(),modifiedAddress)

        def result=pharmacyRepository.findAll()

        then:
        result.get(0).getPharmacyAddress()==inputAddress
    }
}
