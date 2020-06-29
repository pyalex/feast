/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2018-2019 The Feast Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package feast.core.dao;

import feast.core.model.FeatureSetJobStatus;
import feast.core.model.Job;
import feast.core.model.JobStatus;
import feast.proto.core.SourceProto;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** JPA repository supplying Job objects keyed by ID. */
@Repository
public interface JobRepository extends JpaRepository<Job, String> {
  Optional<Job>
      findFirstBySourceTypeAndSourceConfigAndStoreNameAndStatusNotInOrderByLastUpdatedDesc(
          SourceProto.SourceType sourceType,
          String sourceConfig,
          String storeName,
          Collection<JobStatus> statuses);

  Optional<Job>
      findFirstBySourceTypeAndSourceConfigAndStoreNameIsNullAndStatusNotInOrderByLastUpdatedDesc(
          SourceProto.SourceType sourceType, String sourceConfig, Collection<JobStatus> statuses);

  List<Job> findByStatus(JobStatus status);

  List<Job> findByFeatureSetJobStatusesIn(List<FeatureSetJobStatus> featureSetsJobStatuses);

  // find jobs by feast store name
  List<Job> findByStoresName(String storeName);
}
