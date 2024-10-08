package qal.fast.configs.generator;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import qal.fast.configs.jackson.LongToStringSerializer;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;

@Setter
@Getter
@MappedSuperclass
public abstract class CustomPanacheEntity extends PanacheEntityBase implements Serializable {

    @Id
    @GeneratedValue(generator = "snowflake", strategy = GenerationType.AUTO)
    @GenericGenerator(name = "snowflake", strategy = "qal.fast.configs.generator.SnowflakeIdGenerator")
    @JsonSerialize(using = LongToStringSerializer.class)
    public Long id;

}
