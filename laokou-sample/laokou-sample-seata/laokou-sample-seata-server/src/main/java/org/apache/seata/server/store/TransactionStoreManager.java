/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.seata.server.store;

import org.apache.seata.core.model.GlobalStatus;
import org.apache.seata.server.session.GlobalSession;
import org.apache.seata.server.session.SessionCondition;

import java.util.List;

/**
 * The interface Transaction store manager.
 *
 */
public interface TransactionStoreManager {

	/**
	 * Write session boolean.
	 * @param logOperation the log operation
	 * @param session the session
	 * @return the boolean
	 */
	boolean writeSession(LogOperation logOperation, SessionStorable session);

	/**
	 * Read global session global session.
	 * @param xid the xid
	 * @return the global session
	 */
	GlobalSession readSession(String xid);

	/**
	 * Read session global session.
	 * @param xid the xid
	 * @param withBranchSessions the withBranchSessions
	 * @return the global session
	 */
	GlobalSession readSession(String xid, boolean withBranchSessions);

	/**
	 * Read session global session by sort by timeout begin status.
	 * @param withBranchSessions the withBranchSessions
	 * @return the global session
	 */
	List<GlobalSession> readSortByTimeoutBeginSessions(boolean withBranchSessions);

	/**
	 * Read session global session.
	 * @param statuses the statuses
	 * @param withBranchSessions the withBranchSessions
	 * @return the global session list
	 */
	List<GlobalSession> readSession(GlobalStatus[] statuses, boolean withBranchSessions);

	/**
	 * Read session by status list.
	 * @param sessionCondition the session condition
	 * @return the list
	 */
	List<GlobalSession> readSession(SessionCondition sessionCondition);

	/**
	 * Shutdown.
	 */
	void shutdown();

	/**
	 * The enum Log operation.
	 */
	enum LogOperation {

		/**
		 * Global add log operation.
		 */
		GLOBAL_ADD((byte) 1),
		/**
		 * Global update log operation.
		 */
		GLOBAL_UPDATE((byte) 2),
		/**
		 * Global remove log operation.
		 */
		GLOBAL_REMOVE((byte) 3),
		/**
		 * Branch add log operation.
		 */
		BRANCH_ADD((byte) 4),
		/**
		 * Branch update log operation.
		 */
		BRANCH_UPDATE((byte) 5),
		/**
		 * Branch remove log operation.
		 */
		BRANCH_REMOVE((byte) 6);

		private byte code;

		LogOperation(byte code) {
			this.code = code;
		}

		/**
		 * Gets code.
		 * @return the code
		 */
		public byte getCode() {
			return this.code;
		}

		/**
		 * Gets log operation by code.
		 * @param code the code
		 * @return the log operation by code
		 */
		public static LogOperation getLogOperationByCode(byte code) {
			for (LogOperation temp : values()) {
				if (temp.getCode() == code) {
					return temp;
				}
			}
			throw new IllegalArgumentException("Unknown LogOperation[" + code + "]");
		}

	}

}
