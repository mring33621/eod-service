package xyz.mattring.marketdata.eodservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import xyz.mattring.marketdata.eodservice.domain.EodPoint;
import xyz.mattring.marketdata.eodservice.record.EodPointRecord;

@Mapper(componentModel = "spring")
public interface EodPointMapper {

    @Mapping(target = "id", source = "idAsString")
    EodPointRecord toRecord(EodPoint entity);

}
