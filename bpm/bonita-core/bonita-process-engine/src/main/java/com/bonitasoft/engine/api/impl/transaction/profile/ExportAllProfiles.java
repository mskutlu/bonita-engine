/**
 * Copyright (C) 2012 BonitaSoft S.A.
 * BonitaSoft, 32 rue Gustave Eiffel - 38000 Grenoble
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation
 * version 2.1 of the License.
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License along with this
 * program; if not, write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth
 * Floor, Boston, MA 02110-1301, USA.
 **/
package com.bonitasoft.engine.api.impl.transaction.profile;

import java.util.List;

import org.bonitasoft.engine.commons.exceptions.SBonitaException;
import org.bonitasoft.engine.identity.IdentityService;
import org.bonitasoft.engine.profile.ProfileService;
import org.bonitasoft.engine.profile.model.SProfile;
import org.bonitasoft.engine.xml.XMLNode;
import org.bonitasoft.engine.xml.XMLWriter;

/**
 * @author Zhao Na
 * @author Celine Souchet
 */
public class ExportAllProfiles extends AbstractExportProfiles {

    public ExportAllProfiles(final ProfileService profileService, final IdentityService identityService, final XMLWriter writer) {
        super(profileService, identityService, writer);
    }

    @Override
    protected XMLNode getProfilesXmlNode() throws SBonitaException {
        final String NS_PREFIX = "profiles";
        final String NAME_SPACE = "http://www.bonitasoft.org/ns/profile/6.0";
        final XMLNode profilesNode = new XMLNode(NS_PREFIX + ":profiles");
        profilesNode.addAttribute("xmlns:" + NS_PREFIX, NAME_SPACE);

        int index = 0;
        List<SProfile> sProfiles = searchProfiles(index);
        while (sProfiles.size() > 0) {
            for (final SProfile sProfile : sProfiles) {
                profilesNode.addChild(getProfileXmlNode(sProfile));
            }
            index++;
            sProfiles = searchProfiles(index);
        }

        return profilesNode;
    }

}
