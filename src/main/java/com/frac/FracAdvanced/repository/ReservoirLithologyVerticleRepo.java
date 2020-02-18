/*
 * package com.frac.FracAdvanced.repository;
 * 
 * import java.util.List;
 * 
 * 
 * import org.springframework.data.jpa.repository.JpaRepository; import
 * org.springframework.data.jpa.repository.Query;
 * 
 * import com.frac.FracAdvanced.model.ProjectDetails; import
 * com.frac.FracAdvanced.model.SingleLayerInputModel;
 * 
 * public interface SingleLayerInputRepo extends
 * JpaRepository<SingleLayerInputModel, Integer> {
 * 
 * 
 * @Query("select t from SingleLayerInputModel t where t.pid=:pid order by t.id"
 * ) List< SingleLayerInputModel> findByrLVerticle(ProjectDetails rLVerticle);
 * 
 * 
 * 
 * }
 */