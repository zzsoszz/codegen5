package ${packageName}.dao;
import ${packageName}.model.*;
import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
public interface ${model.simpleName}Repository  extends PagingAndSortingRepository<${model.simpleName}, Long>,JpaSpecificationExecutor<${model.simpleName}>  {
	
}