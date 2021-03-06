/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.sql.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.resources.fluentcore.arm.ResourceUtils;
import com.microsoft.azure.management.sql.SqlChildrenOperations;
import com.microsoft.azure.management.sql.SqlServer;
import rx.Completable;
import rx.Observable;

import java.util.List;
import java.util.Objects;

/**
 * Implementation for SQL Server children operations.
 *
 * @param <FluentModelT> the fluent model type of the child resource
 */
@LangDefinition
public abstract class SqlChildrenOperationsImpl<FluentModelT>
    implements
        SqlChildrenOperations<FluentModelT>,
        SqlChildrenOperations.SqlChildrenActionsDefinition<FluentModelT> {

    protected SqlServerManager sqlServerManager;
    protected SqlServer sqlServer;

    SqlChildrenOperationsImpl(SqlServer parent, SqlServerManager sqlServerManager) {
        Objects.requireNonNull(sqlServerManager);
        this.sqlServer = parent;
        this.sqlServerManager = sqlServerManager;
    }

    @Override
    public FluentModelT get(String name) {
        if (this.sqlServer == null) {
            return null;
        }
        return this.getBySqlServer(this.sqlServer, name);
    }

    @Override
    public Observable<FluentModelT> getAsync(String name) {
        if (this.sqlServer == null) {
            return null;
        }
        return this.getBySqlServerAsync(this.sqlServer, name);
    }

    @Override
    public FluentModelT getById(String id) {
        Objects.requireNonNull(id);
        return this.getBySqlServer(ResourceUtils.groupFromResourceId(id),
            ResourceUtils.nameFromResourceId(ResourceUtils.parentRelativePathFromResourceId(id)),
            ResourceUtils.nameFromResourceId(id));
    }

    @Override
    public Observable<FluentModelT> getByIdAsync(String id) {
        Objects.requireNonNull(id);
        return this.getBySqlServerAsync(ResourceUtils.groupFromResourceId(id),
            ResourceUtils.nameFromResourceId(ResourceUtils.parentRelativePathFromResourceId(id)),
            ResourceUtils.nameFromResourceId(id));
    }

    @Override
    public void delete(String name) {
        if (this.sqlServer != null) {
            this.deleteBySqlServer(this.sqlServer.resourceGroupName(), this.sqlServer.name(), name);
        }
    }

    @Override
    public Completable deleteAsync(String name) {
        if (this.sqlServer == null) {
            return null;
        }
        return this.deleteBySqlServerAsync(this.sqlServer.resourceGroupName(), this.sqlServer.name(), name);
    }

    @Override
    public void deleteById(String id) {
        Objects.requireNonNull(id);
        this.deleteBySqlServer(ResourceUtils.groupFromResourceId(id),
            ResourceUtils.nameFromResourceId(ResourceUtils.parentRelativePathFromResourceId(id)),
            ResourceUtils.nameFromResourceId(id));
    }

    @Override
    public Completable deleteByIdAsync(String id) {
        Objects.requireNonNull(id);
        return this.deleteBySqlServerAsync(ResourceUtils.groupFromResourceId(id),
            ResourceUtils.nameFromResourceId(ResourceUtils.parentRelativePathFromResourceId(id)),
            ResourceUtils.nameFromResourceId(id));
    }

    @Override
    public List<FluentModelT> list() {
        if (this.sqlServer == null) {
            return null;
        }
        return this.listBySqlServer(this.sqlServer);
    }

    @Override
    public Observable<FluentModelT> listAsync() {
        if (sqlServer == null) {
            return null;
        }
        return this.listBySqlServerAsync(this.sqlServer);
    }
}
